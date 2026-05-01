package kranji.codegen.catalog;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Loads {@code kMandarin} entries from {@code Unihan_Readings.txt} into a
 * codepoint→pinyin map for fast lookup.
 *
 * <p>Format of source lines (tab-separated):</p>
 * <pre>
 * U+5B69	kMandarin	hái
 * U+671F	kMandarin	qī
 * </pre>
 *
 * <p>Some characters have multiple readings (e.g. {@code "qī jī"}) — this
 * reader keeps only the first whitespace-separated token, which is the
 * primary modern Mandarin reading.</p>
 */
public final class UnihanReader {

    private final Map<Integer, String> byCodepoint;

    private UnihanReader(Map<Integer, String> byCodepoint) {
        this.byCodepoint = byCodepoint;
    }

    /** Pinyin (diacritic form) for a glyph string, or empty if not in Unihan. */
    public Optional<String> pinyinOf(String glyph) {
        if (glyph == null || glyph.isEmpty()) return Optional.empty();
        int cp = glyph.codePointAt(0);
        return Optional.ofNullable(byCodepoint.get(cp));
    }

    public int size() { return byCodepoint.size(); }

    public static UnihanReader load(Path unihanFile) throws IOException {
        Map<Integer, String> map = new HashMap<>(45000);
        try (BufferedReader br = Files.newBufferedReader(unihanFile, StandardCharsets.UTF_8)) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.isEmpty() || line.charAt(0) == '#') continue;
                // Format: U+XXXX\tkField\tvalue
                String[] parts = line.split("\t", 3);
                if (parts.length < 3) continue;
                if (!parts[1].equals("kMandarin")) continue;
                String upart = parts[0];
                if (!upart.startsWith("U+")) continue;
                int cp;
                try {
                    cp = Integer.parseInt(upart.substring(2), 16);
                } catch (NumberFormatException e) {
                    continue;
                }
                String pinyin = parts[2].trim();
                int sp = pinyin.indexOf(' ');
                if (sp >= 0) pinyin = pinyin.substring(0, sp);
                if (!pinyin.isEmpty()) map.put(cp, pinyin);
            }
        }
        return new UnihanReader(map);
    }
}
