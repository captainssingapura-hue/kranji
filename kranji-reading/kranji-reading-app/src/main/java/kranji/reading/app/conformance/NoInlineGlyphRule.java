package kranji.reading.app.conformance;

import hue.captains.singapura.js.homing.conformance.rules.DoctrineRef;
import hue.captains.singapura.js.homing.conformance.rules.Finding;
import hue.captains.singapura.js.homing.conformance.rules.JsRule;
import hue.captains.singapura.js.homing.conformance.rules.RuleId;
import hue.captains.singapura.js.homing.conformance.rules.ServedModule;

import java.util.ArrayList;
import java.util.List;

/**
 * A served module must not inline CJK characters in its JavaScript.
 *
 * <p>Kranji's central discipline is that a character is a typed record, never
 * a string literal. This rule carries that discipline one layer out, into the
 * artifact that reaches the browser: glyphs and their phonics arrive from the
 * corpus over the wire, so a widget cannot drift out of step with the
 * registry.</p>
 *
 * <p>The stake is higher here than in a normal style rule. A hardcoded reading
 * in a widget is not untidy - it is a correctness bug that teaches a child
 * something false, and it survives every later correction to the corpus.</p>
 */
public record NoInlineGlyphRule() implements JsRule {

    public static final NoInlineGlyphRule INSTANCE = new NoInlineGlyphRule();

    private static final RuleId ID = new RuleId("no-inline-glyph");

    @Override public RuleId id() { return ID; }

    @Override
    public String intent() {
        return "Characters and their readings come from the corpus over the wire, never from a "
             + "literal in served JavaScript - so a widget cannot drift out of step with the "
             + "typed registry.";
    }

    @Override
    public DoctrineRef basis() {
        return new DoctrineRef("no-stringly-typed-glyphs");
    }

    @Override
    public List<Finding> check(ServedModule module) {
        var findings = new ArrayList<Finding>();
        List<String> lines = module.lines();
        for (int i = 0; i < lines.size(); i++) {
            if (containsHan(lines.get(i))) {
                findings.add(new Finding(module.moduleClass(), ID,
                        "inline CJK character - characters must come from the corpus, "
                      + "not a JavaScript literal",
                        i + 1));
            }
        }
        return List.copyOf(findings);
    }

    /** True when the line carries a codepoint in the Han script. */
    private static boolean containsHan(String line) {
        return line.codePoints().anyMatch(NoInlineGlyphRule::isHan);
    }

    private static boolean isHan(int cp) {
        try {
            return Character.UnicodeScript.of(cp) == Character.UnicodeScript.HAN;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
