package kranji.reading.app.conformance;

import hue.captains.singapura.js.homing.conformance.rules.Finding;
import hue.captains.singapura.js.homing.conformance.rules.ServedModule;
import hue.captains.singapura.js.homing.core.StandardJsModuleType;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for {@link NoInlineGlyphRule}.
 *
 * <p>A rule nobody has seen fail is a rule nobody knows works. These feed the
 * rule synthetic served modules rather than relying on a real widget happening
 * to violate it.</p>
 */
class NoInlineGlyphRuleTest {

    private static ServedModule js(String... lines) {
        return ServedModule.of("kranji.reading.test.Probe",
                StandardJsModuleType.CONSUMER,
                String.join("\n", lines));
    }

    @Test
    void flagsAnInlineHanCharacter() {
        List<Finding> findings = NoInlineGlyphRule.INSTANCE.check(
                js("var root = branch.createElement('root', 'div');",
                   "root.textContent = '清';"));

        assertEquals(1, findings.size(), "one inline glyph should produce one finding");
        assertEquals(2, findings.get(0).line(), "the finding should point at the offending line");
        assertTrue(findings.get(0).message().contains("corpus"),
                "the message should say where characters are supposed to come from");
    }

    @Test
    void flagsEveryOffendingLineSeparately() {
        List<Finding> findings = NoInlineGlyphRule.INSTANCE.check(
                js("a.textContent = '水';",
                   "b.textContent = 'plain ascii';",
                   "c.textContent = '火';"));

        assertEquals(2, findings.size());
        assertEquals(List.of(1, 3), findings.stream().map(Finding::line).toList());
    }

    @Test
    void passesCleanJavaScript() {
        List<Finding> findings = NoInlineGlyphRule.INSTANCE.check(
                js("var root = branch.createElement('root', 'div');",
                   "css.setClass(root, kr_widget_root);",
                   "root.textContent = payload.glyph;"));

        assertEquals(List.of(), findings, "characters fetched from the corpus are the point");
    }

    @Test
    void ignoresPinyinAndPunctuation() {
        // Latin pinyin with tone marks is not a Han glyph and must not trip the
        // rule - annotations are legitimately present in served code.
        List<Finding> findings = NoInlineGlyphRule.INSTANCE.check(
                js("var reading = 'háng';",
                   "var sep = '\\u00b7';"));

        assertEquals(List.of(), findings);
    }
}
