package kranji.studio.gloss;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

/**
 * What a person decided about a seeded row, read from the one file the
 * generator never writes.
 *
 * <h2>Why it is not in the flags file</h2>
 *
 * <p>{@code pNNN.flags.tsv} is output. {@code GlossSeedMain all} rewrites all
 * 101 of them, so a verdict recorded there survives exactly until the next
 * regeneration — and regeneration is not rare, it is how a better
 * sense-selection policy reaches the data. This is the sidecar decision gc3
 * asks for, in its simplest form: a separate file, keyed on the pair.</p>
 *
 * <h2>Why it records what was reviewed</h2>
 *
 * <p>A verdict carries the {@code kind} and {@code doubts} it was given
 * against, not just the pair. Without that, regenerating with a different
 * policy could raise an entirely new problem for a pair somebody had already
 * signed off, and the workbench would show it as done. The plan's phrasing is
 * exact: no verdict outlives its subject.</p>
 */
public final class SeedVerdicts {

    private static final String RESOURCE = "/kranji/seed/verdicts.tsv";

    private SeedVerdicts() {}

    /** What a reviewer may say. Deliberately small — gc4 is still open. */
    public enum Verdict { OK, FIXED, WONTFIX }

    /**
     * One decision.
     *
     * @param kind   the problem as it stood when this was decided
     * @param doubts the doubts as they stood, comma-separated, possibly empty
     */
    public record Entry(int codePoint, String reading, String kind, String doubts,
                        Verdict verdict, String note) {

        /** The key a problem row is matched on. */
        public String pairKey() { return codePoint + ":" + reading; }

        /** Whether this was decided against the problem the generator now raises. */
        public boolean covers(String currentKind, String currentDoubts) {
            return kind.equals(currentKind) && doubts.equals(currentDoubts);
        }
    }

    private static final Map<String, Entry> BY_PAIR = load();

    private static Map<String, Entry> load() {
        var out = new LinkedHashMap<String, Entry>();
        int lineNumber = 0;
        for (String line : read().split("\n", -1)) {
            lineNumber++;
            String row = line.strip();
            if (row.isEmpty() || row.charAt(0) == '#') continue;

            String[] f = line.split("\t", -1);
            if (f.length < 6) {
                throw new IllegalStateException(RESOURCE + ":" + lineNumber
                        + " has " + f.length + " columns, expected 6: " + row);
            }
            Entry entry = new Entry(codePointOf(f[0], lineNumber), f[1].strip(),
                    f[2].strip(), f[3].strip(), verdictOf(f[4], lineNumber), f[5].strip());
            Entry clash = out.put(entry.pairKey(), entry);
            if (clash != null) {
                // Two verdicts for one pair is not a merge to resolve silently
                // - one of them is somebody's decision being thrown away.
                throw new IllegalStateException(RESOURCE + ":" + lineNumber
                        + " decides " + entry.pairKey() + " twice");
            }
        }
        // Not Map.copyOf: unspecified iteration order, and all() is read out
        // again to list the verdicts whose problem has gone.
        return Collections.unmodifiableMap(out);
    }

    private static int codePointOf(String field, int lineNumber) {
        try {
            return Integer.parseInt(field.strip());
        } catch (NumberFormatException notANumber) {
            throw new IllegalStateException(RESOURCE + ":" + lineNumber
                    + " has no decimal codepoint: '" + field + "'", notANumber);
        }
    }

    private static Verdict verdictOf(String field, int lineNumber) {
        try {
            return Verdict.valueOf(field.strip().toUpperCase(java.util.Locale.ROOT));
        } catch (IllegalArgumentException notAVerdict) {
            throw new IllegalStateException(RESOURCE + ":" + lineNumber
                    + " says '" + field.strip() + "'; the vocabulary is "
                    + java.util.Arrays.toString(Verdict.values()), notAVerdict);
        }
    }

    private static String read() {
        try (InputStream in = SeedVerdicts.class.getResourceAsStream(RESOURCE)) {
            // Absent is legitimate and means nothing has been reviewed. A build
            // without the gloss module has no seed to review either.
            if (in == null) return "";
            return new String(in.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new UncheckedIOException("could not read " + RESOURCE, e);
        }
    }

    /** The decision for a pair, if anybody has made one. */
    public static Optional<Entry> find(int codePoint, String reading) {
        return Optional.ofNullable(BY_PAIR.get(codePoint + ":" + reading));
    }

    /** Every decision, in file order. */
    public static Map<String, Entry> all() { return BY_PAIR; }
}
