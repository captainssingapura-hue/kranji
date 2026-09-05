package kranji.codegen.phonic;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Readings the source writes in a form the standard does not use, corrected
 * before anything tries to parse them.
 *
 * <h2>Why the correction is here and not in the pinyin model</h2>
 *
 * <p>Both entries in the file are <b>spelling</b> problems in the input, not
 * gaps in what this build can model. Teaching {@link kranji.pinyin.PinyinSyllable}
 * to accept {@code ê̄} would say that {@code ê} is a final this system
 * recognises, which is a claim about phonology; correcting the input says only
 * that kTGHZ2013 wrote a syllable in a notation the standard replaced, which is
 * a claim about a file. The second is the true one.</p>
 *
 * <p>Applied in the generator, so the corpus files carry the corrected reading
 * and every consumer downstream — the reader, the studio, the gloss seeder —
 * sees one story. A correction applied at read time would leave the committed
 * data disagreeing with the model that loads it.</p>
 *
 * <h2>A file, not a map in Java</h2>
 *
 * <p>The same reasoning {@code HandCrafted} records for the gloss set: a row is
 * editable, sortable and greppable in a way a nested builder is not, and the
 * two spellings this table is about are a base letter plus a combining mark
 * against a single precomposed character — indistinguishable on screen. In
 * Java they were four string literals nobody could check. In a TSV beside the
 * other data they are rows, each carrying the reason it exists, and a person
 * adding the next one does not have to open an editor that compiles.</p>
 *
 * <p>The checking did not go away. This refuses a malformed file loudly rather
 * than silently correcting nothing, which is the failure that would otherwise
 * reappear as a corpus quietly reverting.</p>
 */
public final class SourceCorrections {

    private static final String RESOURCE = "/kranji/codegen/phonic/reading-corrections.tsv";

    private SourceCorrections() {}

    /** {@code codepoint -> (reading as written -> reading as standard, or "" to drop)}. */
    private static final Map<Integer, Map<String, String>> BY_CHARACTER = load();

    private static Map<Integer, Map<String, String>> load() {
        var out = new LinkedHashMap<Integer, Map<String, String>>();
        int lineNumber = 0;
        for (String line : read().split("\n", -1)) {
            lineNumber++;
            String row = line.strip();
            if (row.isEmpty() || row.charAt(0) == '#') continue;

            // -1, so a DROP row - whose standard column is empty and whose why
            // column follows it - does not lose its trailing fields.
            String[] f = line.split("\t", -1);
            if (f.length < 5) {
                throw new IllegalStateException(RESOURCE + ":" + lineNumber
                        + " has " + f.length + " columns, expected 5: " + row);
            }
            int codePoint = codePointOf(f[0], lineNumber);
            String written = f[2].strip();
            if (written.isEmpty()) {
                throw new IllegalStateException(RESOURCE + ":" + lineNumber
                        + " corrects nothing - the 'as written' column is empty");
            }
            if (f[4].strip().isEmpty()) {
                // Every row owes a reason. Without one this becomes a list of
                // edits nobody can audit, which is how a correction table turns
                // into a second, private standard.
                throw new IllegalStateException(RESOURCE + ":" + lineNumber
                        + " does not say why: " + row);
            }
            out.computeIfAbsent(codePoint, k -> new LinkedHashMap<>())
               .put(written, f[3].strip());
        }
        // Not Map.copyOf: its iteration order is unspecified, and corrected()
        // hands this order out. File order is the order a person edits in.
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

    private static String read() {
        try (InputStream in = SourceCorrections.class.getResourceAsStream(RESOURCE)) {
            if (in == null) {
                throw new IllegalStateException(RESOURCE + " is not on the classpath - "
                        + "a build without it regenerates a corpus that quietly reverts");
            }
            return new String(in.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new UncheckedIOException("could not read " + RESOURCE, e);
        }
    }

    /**
     * The character's readings, corrected. Order is preserved and a dropped
     * reading simply is not there.
     */
    public static List<String> apply(int codePoint, List<String> readings) {
        Map<String, String> fixes = BY_CHARACTER.get(codePoint);
        if (fixes == null) return readings;

        var out = new ArrayList<String>(readings.size());
        for (String reading : readings) {
            String fixed = fixes.getOrDefault(reading, reading);
            if (!fixed.isEmpty()) out.add(fixed);
        }
        return out;
    }

    /** Which characters carry a correction, for a test that pins the list. */
    public static List<Integer> corrected() {
        return List.copyOf(BY_CHARACTER.keySet());
    }

    /** How many readings the table rewrites or drops, across all characters. */
    public static int size() {
        return BY_CHARACTER.values().stream().mapToInt(Map::size).sum();
    }
}
