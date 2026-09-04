package kranji.codegen.gloss;

import kranji.gloss.tsv.GlossTsv;
import kranji.phonic.PhonicPartitions;
import kranji.phonic.SourceReadings;
import kranji.simple.gloss.Meaning;
import kranji.simple.gloss.Priority;
import kranji.simple.gloss.RankingInfo;
import kranji.simple.gloss.Sense;
import kranji.simple.gloss.SoundGloss;
import kranji.simple.gloss.ZiGloss;
import kranji.zi.ZiPartition;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Seeds one partition's glosses from Unihan's {@code kDefinition}.
 *
 * <pre>
 *   GlossSeedMain &lt;partition&gt; [unihan] [outDir]
 * </pre>
 *
 * <h2>Monophonic characters only</h2>
 *
 * <p>{@code kDefinition} glosses a CHARACTER and this model keys on a
 * (character, reading) pair. For a character with one reading those are the
 * same thing and the seed lands unambiguously — 7,506 of the corpus's 8,100.
 * For a polyphone they are not, and no per-character dictionary can say which
 * of its senses belongs to which reading: 地 is <i>earth</i> as dì and a
 * particle as de, and a field listing both says nothing about which is which.</p>
 *
 * <p>So polyphones are <b>queued, not guessed</b>. A wrong split is worse than
 * an absent one, because it looks finished.</p>
 *
 * <h2>Built through the model, not printed</h2>
 *
 * <p>The rows go into {@link ZiGloss} instances and out through
 * {@link GlossTsv#writeSenses}, which is the same writer the hand-crafted set
 * round-trips through. Anything the model refuses — a non-canonical reading, a
 * gap in the sense numbering — fails here rather than becoming a file that
 * parses into something subtly different.</p>
 *
 * <h2>Deterministic</h2>
 *
 * <p>Ordered by codepoint, so an unchanged input produces an unchanged diff and
 * re-running is a no-op. A generator whose output churns cannot be re-run, and
 * one that cannot be re-run is one nobody re-runs when the source moves.</p>
 */
public final class GlossSeedMain {

    /** Where Unihan's readings file is expected unless told otherwise. */
    private static final String DEFAULT_UNIHAN = "input/_2500/Unihan_Readings.txt";

    /** Where seeded partitions are written. */
    private static final String DEFAULT_OUT = "kranji-gloss-seed/src/main/resources/kranji/seed";

    private GlossSeedMain() {}

    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.err.println("Usage: GlossSeedMain <partition|all> [unihan] [outDir]");
            System.exit(2);
            return;
        }
        Path unihan = Path.of(args.length > 1 ? args[1] : DEFAULT_UNIHAN);
        Path outDir = Path.of(args.length > 2 ? args[2] : DEFAULT_OUT);
        Map<Integer, String> definitions = definitions(unihan);
        Files.createDirectories(outDir);

        if (args[0].trim().equalsIgnoreCase("all")) {
            seedAll(definitions, outDir);
            return;
        }

        int partition = Integer.parseInt(args[0].trim());
        if (!ZiPartition.exists(partition)) {
            System.err.println("No partition " + partition + " - there are " + ZiPartition.COUNT);
            System.exit(2);
            return;
        }
        write(partition, seed(partition, definitions), outDir);
    }

    /**
     * Every partition, with a running total.
     *
     * <p>The console is not the report. Windows mangles CJK on stdout, so the
     * per-partition detail goes to a file beside the data where the glyphs
     * survive and the workbench can read it later; the terminal gets counts,
     * which are the part it can render.</p>
     */
    private static void seedAll(Map<Integer, String> definitions, Path outDir)
            throws IOException {
        int seeded = 0, flagged = 0, queued = 0, unseeded = 0;
        for (int p = 0; p < ZiPartition.COUNT; p++) {
            Result result = seed(p, definitions);
            write(p, result, outDir);
            seeded += result.seededPairs();
            flagged += result.flagged().size();
            queued += result.queuedPolyphone().size();
            unseeded += result.noSource().size();
        }
        System.out.printf("""
                %d partitions
                  seeded    %d pairs
                  flagged   %d seeded rows want a person's eye
                  queued    %d polyphones to split by hand
                  unseeded  %d characters have no usable kDefinition
                Detail per partition: %s/pNNN.flags.tsv
                """, ZiPartition.COUNT, seeded, flagged, queued, unseeded, outDir);
    }

    /**
     * Where these rows came from, on every file that carries them.
     *
     * <p>Reproduced from the header of the drop itself rather than recalled,
     * and repeated per file rather than kept once in a NOTICE, because a file
     * gets copied out of a repository far more often than a repository gets
     * read. The phonic partitions already carry the same line; these did not,
     * which was an omission in the first seeder run.</p>
     *
     * <p>Written here and not in {@link GlossTsv}, which the hand-crafted set
     * shares — that data is authored for the project and owes Unicode nothing,
     * and stamping this on it would be a false claim in the other direction.</p>
     */
    private static final String PROVENANCE = """
            # Seeded from the Unicode Character Database (Unihan), field kDefinition.
            # (c) 2025 Unicode, Inc. Unicode and the Unicode Logo are registered
            # trademarks of Unicode, Inc. in the U.S. and other countries.
            # For terms of use and license, see https://www.unicode.org/terms_of_use.html
            # Generated by GlossSeedMain - regenerate rather than edit.
            #
            # NOT CHECKED BY ANYBODY. A machine's first reading of a dictionary
            # field, kept apart from the hand-written set for that reason.
            """;

    /** The partition's rows, and the log of what it could not do cleanly. */
    private static void write(int partition, Result result, Path outDir) throws IOException {
        Files.writeString(outDir.resolve(name(partition) + ".tsv"),
                          PROVENANCE + result.tsv(), StandardCharsets.UTF_8);
        Files.writeString(outDir.resolve(name(partition) + ".flags.tsv"),
                          PROVENANCE + result.flagFile(partition), StandardCharsets.UTF_8);
    }

    /** What the seeder could not do cleanly, and to which character. */
    public enum Kind {
        /** Seeded, but something about the selection wants confirming. */
        FLAGGED,
        /** A polyphone. Its senses need splitting across its readings by hand. */
        QUEUED,
        /** Nothing usable to seed from. Somebody has to write one. */
        UNSEEDED
    }

    /**
     * One row of a partition's log.
     *
     * <p>Structured rather than a formatted line, because this is going into a
     * file the workbench will read. A log a person can grep and a tool cannot
     * load is a log that gets re-derived by hand.</p>
     */
    public record Note(int codePoint, String glyph, String reading,
                       Kind kind, String doubts, String detail) {}

    /** The seeded rows, and what could not be seeded. */
    public record Result(String tsv, List<Note> notes, int seededPairs) {

        public Result {
            notes = List.copyOf(notes);
        }

        public List<Note> of(Kind kind) {
            return notes.stream().filter(n -> n.kind() == kind).toList();
        }

        public List<Note> flagged()         { return of(Kind.FLAGGED); }
        public List<Note> queuedPolyphone() { return of(Kind.QUEUED); }
        public List<Note> noSource()        { return of(Kind.UNSEEDED); }

        /** Counts, for a terminal that cannot render the glyphs anyway. */
        public String report(int partition) {
            return """
                   Partition %d
                     seeded      %d characters, %d pairs
                     flagged     %d of those want a person's eye
                     queued      %d polyphones - a per-character field cannot split them
                     unseeded    %d characters have no usable kDefinition
                   """.formatted(partition, seededPairs, seededPairs,
                                 flagged().size(), queuedPolyphone().size(),
                                 noSource().size());
        }

        /** The log, as a file - one row per thing a reviewer has to look at. */
        public String flagFile(int partition) {
            var out = new StringBuilder();
            out.append("# Seeded partition ").append(partition)
               .append(" - what the seeder could not do cleanly.\n");
            out.append("# Generated by GlossSeedMain; regenerate rather than edit.\n");
            out.append("# FLAGGED  seeded, check before trusting\n");
            out.append("# QUEUED   a polyphone; split its senses across its readings\n");
            out.append("# UNSEEDED nothing usable to seed from; write one\n");
            out.append("# codepoint\tglyph\treading\tkind\tdoubts\tdetail\n");
            for (Note n : notes) {
                out.append(n.codePoint()).append('\t')
                   .append(n.glyph()).append('\t')
                   .append(n.reading()).append('\t')
                   .append(n.kind()).append('\t')
                   .append(n.doubts()).append('\t')
                   .append(n.detail()).append('\n');
            }
            return out.toString();
        }
    }

    /** Two digits, so the files sort the way the partitions are numbered. */
    static String name(int partition) {
        return String.format("p%03d", partition);
    }

    /**
     * Build one partition. Visible for testing — takes the definitions as a map
     * so a test does not need the 8MB drop on disk.
     */
    public static Result seed(int partition, Map<Integer, String> definitions) {
        // TreeMap: codepoint order, so the output is stable across runs and a
        // regeneration diffs to nothing when the input has not moved.
        var rows = new TreeMap<Integer, ZiGloss>();
        var notes = new ArrayList<Note>();

        for (SourceReadings row : PhonicPartitions.loadAll()) {
            int cp = row.zi().codePoint();
            if (ZiPartition.of(cp) != partition) continue;

            if (row.isPolyphonic()) {
                notes.add(new Note(cp, row.zi().value(), row.principal().numbered(),
                        Kind.QUEUED, "", String.join(" ", row.readingTexts())));
                continue;
            }

            GlossSeedPolicy.Seed seed = GlossSeedPolicy.of(definitions.get(cp));
            // Joined, not List.toString(): the brackets are Java leaking into a
            // data file, and a reviewer's verdict records this string - so
            // "[TRUNCATED]" against "TRUNCATED" reads as a changed problem.
            String doubts = seed.doubts().stream().map(Enum::name)
                                .collect(java.util.stream.Collectors.joining(", "));

            if (!seed.usable()) {
                // The doubts travel with it: "no kDefinition at all" and "had one,
                // nothing survived" both end here and are different jobs. 岷 has a
                // gloss - it is 72 characters, and Meaning takes 60.
                notes.add(new Note(cp, row.zi().value(), row.principal().numbered(),
                        Kind.UNSEEDED, doubts, raw(definitions.get(cp))));
                continue;
            }
            if (seed.wantsReview()) {
                // The raw field, not just what was kept: a reviewer judging a
                // TRUNCATED row needs to see what was left behind, and going
                // back to an 8MB drop for it is how a flag gets ignored.
                notes.add(new Note(cp, row.zi().value(), row.principal().numbered(),
                        Kind.FLAGGED, doubts, raw(definitions.get(cp))));
            }
            rows.put(cp, glossOf(row, seed));
        }

        int pairs = rows.size();   // monophonic: one character is one pair
        return new Result(GlossTsv.writeSenses(List.copyOf(rows.values())),
                          List.copyOf(notes), pairs);
    }

    /** One character's seeded gloss, at its single reading. */
    private static ZiGloss glossOf(SourceReadings row, GlossSeedPolicy.Seed seed) {
        var senses = new LinkedHashMap<Meaning, Sense>();
        List<String> meanings = seed.meanings();
        for (int i = 0; i < meanings.size(); i++) {
            // The band follows the position because that is all a dictionary
            // field says: the first gloss is the one it leads with, and nothing
            // in the source ranks the rest. A reviewer re-ranks; a generator
            // asserting a considered priority would be inventing one.
            senses.put(Meaning.of(meanings.get(i)),
                       new Sense(RankingInfo.of(bandFor(i), i), Map.of()));
        }
        return new ZiGloss(row.zi(),
                List.of(new SoundGloss(row.zi(), row.principal(), senses)));
    }

    private static Priority bandFor(int index) {
        return switch (index) {
            case 0  -> Priority.PRIMARY;
            case 1  -> Priority.SECONDARY;
            default -> Priority.AUXILIARY;
        };
    }

    /** The source field, flattened so it cannot break the row it is logged in. */
    private static String raw(String kDefinition) {
        if (kDefinition == null) return "<no kDefinition>";
        return kDefinition.replaceAll("\\s+", " ").trim();
    }

    /** {@code codepoint -> kDefinition}, for every character the drop defines. */
    static Map<Integer, String> definitions(Path unihan) throws IOException {
        var out = new LinkedHashMap<Integer, String>();
        for (String line : Files.readAllLines(unihan, StandardCharsets.UTF_8)) {
            if (line.isEmpty() || line.charAt(0) == '#') continue;
            String[] f = line.split("\t", 3);
            if (f.length < 3 || !f[1].equals("kDefinition")) continue;
            try {
                out.put(Integer.parseInt(f[0].substring(2), 16), f[2]);
            } catch (RuntimeException notACodepoint) {
                // A malformed line in a vendored drop is the drop's problem and
                // not a reason to abandon 23,000 good ones.
            }
        }
        return out;
    }
}
