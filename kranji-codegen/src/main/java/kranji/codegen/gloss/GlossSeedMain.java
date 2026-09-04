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
    private static final String DEFAULT_OUT = "kranji-gloss/src/main/resources/kranji/gloss/seed";

    private GlossSeedMain() {}

    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.err.println("Usage: GlossSeedMain <partition 0.." + (ZiPartition.COUNT - 1)
                             + "> [unihan] [outDir]");
            System.exit(2);
            return;
        }
        int partition = Integer.parseInt(args[0].trim());
        if (!ZiPartition.exists(partition)) {
            System.err.println("No partition " + partition + " - there are " + ZiPartition.COUNT);
            System.exit(2);
            return;
        }
        Path unihan = Path.of(args.length > 1 ? args[1] : DEFAULT_UNIHAN);
        Path outDir = Path.of(args.length > 2 ? args[2] : DEFAULT_OUT);

        Result result = seed(partition, definitions(unihan));

        Files.createDirectories(outDir);
        Path out = outDir.resolve(name(partition) + ".tsv");
        Files.writeString(out, result.tsv(), StandardCharsets.UTF_8);

        System.out.println(result.report(partition));
        System.out.println("Written: " + out);
    }

    /** The seeded rows, and what could not be seeded. */
    public record Result(String tsv, List<String> queuedPolyphone,
                         List<String> noSource, List<String> flagged, int seededPairs) {

        /**
         * What happened, and to which characters.
         *
         * <p>Named rather than counted. "7 flagged" tells a reviewer how much
         * work there is and nothing about where it is, so it is a number they
         * have to go and re-derive before they can act on it.</p>
         */
        public String report(int partition) {
            var out = new StringBuilder("""
                   Partition %d
                     seeded      %d characters, %d pairs
                     flagged     %d of those want a person's eye
                     queued      %d polyphones - a per-character field cannot split them
                     unseeded    %d characters have no usable kDefinition
                   """.formatted(partition, seededPairs, seededPairs,
                                 flagged.size(), queuedPolyphone.size(), noSource.size()));
            listInto(out, "FLAGGED - seeded, but check before trusting", flagged);
            listInto(out, "QUEUED - split the senses across the readings by hand", queuedPolyphone);
            listInto(out, "NOT SEEDABLE - write one; the doubt says why", noSource);
            return out.toString();
        }

        private static void listInto(StringBuilder out, String heading, List<String> items) {
            if (items.isEmpty()) return;
            out.append('\n').append(heading).append('\n');
            for (String item : items) out.append("  ").append(item).append('\n');
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
        var queued = new ArrayList<String>();
        var noSource = new ArrayList<String>();
        var flagged = new ArrayList<String>();

        for (SourceReadings row : PhonicPartitions.loadAll()) {
            int cp = row.zi().codePoint();
            if (ZiPartition.of(cp) != partition) continue;

            if (row.isPolyphonic()) {
                queued.add(row.zi().value() + " " + row.readingTexts());
                continue;
            }

            GlossSeedPolicy.Seed seed = GlossSeedPolicy.of(definitions.get(cp));
            if (!seed.usable()) {
                // The doubts travel with it: "no kDefinition at all" and "had one,
                // nothing survived" both end here and are different jobs. 岷 has a
                // gloss - it is 72 characters, and Meaning takes 60.
                noSource.add(row.zi().value() + "  " + row.zi().codePointLabel()
                           + "  " + seed.doubts());
                continue;
            }
            if (seed.wantsReview()) {
                flagged.add(row.zi().value() + "  " + row.principal().numbered()
                          + "  " + seed.doubts() + "  -> " + seed.meanings());
            }
            rows.put(cp, glossOf(row, seed));
        }

        int pairs = rows.size();   // monophonic: one character is one pair
        return new Result(GlossTsv.writeSenses(List.copyOf(rows.values())),
                          List.copyOf(queued), List.copyOf(noSource),
                          List.copyOf(flagged), pairs);
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
