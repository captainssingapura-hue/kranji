package kranji.phonic;

import kranji.pinyin.Initial;
import kranji.pinyin.PinyinSyllable;
import kranji.zi.ZiCharUTF8;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Reads the phonic source partitions off the classpath.
 *
 * <p>One file per pinyin initial under {@code /kranji/phonic}, each a few
 * hundred tab-separated rows. The whole standard set is 264 KB across twenty-
 * two files, so a partition loads in a few milliseconds and nothing here
 * caches - a caller that wants the set held in memory can hold it.</p>
 *
 * <p>The files are generated from Unihan by {@code PhonicPartitionMain} and
 * carry every reading the source gives, including the handful this build
 * cannot parse. Parsing happens here rather than at partition time, so what
 * cannot be modelled is visible as data instead of missing.</p>
 */
public final class PhonicPartitions {

    private static final String DIR = "/kranji/phonic/";

    private PhonicPartitions() {}

    /** The characters filed under one initial, in codepoint order. */
    public static List<SourceReadings> load(Initial initial) {
        String resource = DIR + segment(initial) + ".tsv";
        try (InputStream in = PhonicPartitions.class.getResourceAsStream(resource)) {
            if (in == null) {
                throw new IllegalStateException("missing phonic partition: " + resource
                      + " - run PhonicPartitionMain to generate it");
            }
            return parse(in);
        } catch (IOException e) {
            throw new UncheckedIOException("reading " + resource, e);
        }
    }

    /** Every partition, in initial order. */
    public static List<SourceReadings> loadAll() {
        var out = new ArrayList<SourceReadings>(8_200);
        for (Initial initial : Initial.values()) out.addAll(load(initial));
        return List.copyOf(out);
    }

    /** Characters per initial, without holding the readings. */
    public static Map<Initial, Integer> sizes() {
        Map<Initial, Integer> out = new LinkedHashMap<>();
        for (Initial initial : Initial.values()) out.put(initial, load(initial).size());
        return Map.copyOf(out);
    }

    // ── Parsing ────────────────────────────────────────────────────────

    private static List<SourceReadings> parse(InputStream in) throws IOException {
        var out = new ArrayList<SourceReadings>();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(in, StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.isEmpty() || line.charAt(0) == '#') continue;
                SourceReadings row = row(line);
                if (row != null) out.add(row);
            }
        }
        return List.copyOf(out);
    }

    /**
     * {@code codepoint \t glyph \t readings \t mandarin \t frequency}.
     *
     * <p>A row whose readings all fail to parse yields {@code null}: without a
     * principal there is nothing to file. The partitioner already excludes
     * those, so this is a guard rather than an expected path.</p>
     */
    private static SourceReadings row(String line) {
        String[] cells = line.split("\t", -1);
        if (cells.length < 3) return null;

        int codePoint = Integer.parseInt(cells[0].substring(2), 16);
        List<String> readings = cells[2].isEmpty() ? List.of()
                : List.of(cells[2].split(","));
        String mandarin = cells.length > 3 ? cells[3] : "";
        ReadingFrequency frequency = ReadingFrequency.parse(
                cells.length > 4 ? cells[4] : "");

        var parsed = new ArrayList<PinyinSyllable>();
        var unparseable = new ArrayList<String>();
        for (String reading : readings) {
            try { parsed.add(PinyinSyllable.parse(reading)); }
            catch (RuntimeException e) { unparseable.add(reading); }
        }
        if (parsed.isEmpty()) return null;

        PinyinSyllable principal = principalOf(parsed, mandarin);
        var alternates = new ArrayList<>(parsed);
        alternates.remove(principal);

        return new SourceReadings(new ZiCharUTF8(codePoint), principal,
                alternates, mandarin, frequency, unparseable);
    }

    /**
     * Unihan's nomination wins when it is one of the standard readings.
     *
     * <p>For 26 characters it is not - the two authorities disagree outright
     * about what the character reads. The standard wins there, because it is
     * the authority on which readings exist at all, and the conflict is
     * reported rather than absorbed.</p>
     */
    private static PinyinSyllable principalOf(List<PinyinSyllable> parsed, String mandarin) {
        for (PinyinSyllable s : parsed) {
            if (s.toDiacritic().equals(mandarin)) return s;
        }
        return parsed.get(0);
    }

    /** Matches the addressing the phonic projection already uses. */
    public static String segment(Initial initial) {
        return initial == Initial.ZERO ? "zero" : initial.pinyin();
    }
}
