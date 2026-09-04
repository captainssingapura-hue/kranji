package kranji.simple;

import kranji.pinyin.PinyinSyllable;
import kranji.zi.ZiCharUTF8;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Checks a set of syllable declarations before they become a registry.
 *
 * <p>Decoupling sound from shape removes every special case from authoring —
 * a polyphonic character is simply declared more than once. What it cannot
 * remove is the question of which reading is principal, and that answer now
 * lives in emphasis spread across separate declarations. Spread-out facts need
 * checking, so this is where that happens.</p>
 *
 * <p>Findings carry a severity because the two failures differ in kind. A
 * character with no principal reading is unusable — nothing can decide what to
 * show for it. A character emphasised twice is a contradiction someone has to
 * resolve. Both are errors; the level exists so a future advisory check has
 * somewhere to sit without being confused for a defect.</p>
 */
public final class PhonicDeclarations {

    private PhonicDeclarations() {}

    public enum Severity { ERROR, WARNING }

    /** One thing wrong with a declaration set. */
    public record Finding(Severity severity, ZiCharUTF8 glyph, String message) {
        @Override public String toString() {
            return severity + " " + glyph + " (" + glyph.codePointLabel() + ") - " + message;
        }
    }

    /** Everything wrong with {@code declarations}, in character order. */
    public static List<Finding> check(List<SyllableDecl> declarations) {
        Map<ZiCharUTF8, List<PinyinSyllable>> principal = new LinkedHashMap<>();
        Map<ZiCharUTF8, List<PinyinSyllable>> all = new LinkedHashMap<>();

        for (SyllableDecl decl : declarations) {
            for (ZiDecl zi : decl.characters()) {
                all.computeIfAbsent(zi.zi(), k -> new ArrayList<>()).add(decl.syllable());
                if (zi.principal()) {
                    principal.computeIfAbsent(zi.zi(), k -> new ArrayList<>()).add(decl.syllable());
                }
            }
        }

        var findings = new ArrayList<Finding>();
        for (var entry : all.entrySet()) {
            ZiCharUTF8 glyph = entry.getKey();
            List<PinyinSyllable> emphasised = principal.getOrDefault(glyph, List.of());

            if (emphasised.isEmpty()) {
                findings.add(new Finding(Severity.ERROR, glyph,
                        "no principal reading - declared only as an alternate, under "
                      + readings(entry.getValue())
                      + ". One appearance must use ZiDecl.of rather than ZiDecl.alt."));
            } else if (emphasised.size() > 1) {
                findings.add(new Finding(Severity.ERROR, glyph,
                        "principal in more than one place - " + readings(emphasised)
                      + ". Exactly one appearance may be emphasised; the rest are alternates."));
            }
        }
        return List.copyOf(findings);
    }

    /** Throws when {@code declarations} carry any error. */
    public static void requireValid(List<SyllableDecl> declarations) {
        List<Finding> findings = check(declarations);
        List<Finding> errors = findings.stream()
                .filter(f -> f.severity() == Severity.ERROR).toList();
        if (!errors.isEmpty()) {
            var sb = new StringBuilder("phonic declarations are invalid:");
            for (Finding f : errors) sb.append("\n  ").append(f);
            throw new IllegalStateException(sb.toString());
        }
    }

    private static String readings(List<PinyinSyllable> syllables) {
        return String.join(", ", syllables.stream().map(PinyinSyllable::toDiacritic).toList());
    }
}
