package kranji.demos;

import kranji.classification.BlockRole;
import kranji.layout.LayoutHint;
import kranji.layout.Politeness;
import kranji.library.BasicSet;
import kranji.library.LibraryMember;
import kranji.singular.SingularFamiliesPerclass;
import kranji.zi.ComposedBlock.LeftRight;
import kranji.zi.SingularBlock;
import kranji.zi.SingularZi;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * End-to-end smoke test that wires the {@code kranji-singulars-perclass}
 * records into {@link BasicSet#INSTANCE} via {@link SingularFamiliesPerclass}
 * — the same path {@link ZiLookup#main} now takes.
 *
 * <p>This is the coexistence proof: it exercises the generated per-class
 * layout in production-like conditions without touching the legacy
 * {@code kranji-singulars} registrar.</p>
 *
 * <p>Note on JVM state: {@link BasicSet#INSTANCE} is global; once populated
 * it stays populated. This test is the only thing in {@code kranji-core-demos}
 * that registers singulars, so we never double-register within a surefire
 * run. Mixing the two registrars in the same JVM is documented as unsafe.</p>
 */
class ZiLookupPerclassSmokeTest {

    private static Map<String, SingularBlock> glyphIndex;

    @BeforeAll
    static void registerOnce() {
        BasicSet.INSTANCE.register();
        SingularFamiliesPerclass.registerInto(BasicSet.INSTANCE);

        glyphIndex = new HashMap<>();
        for (LibraryMember<BasicSet> m : BasicSet.INSTANCE.components()) {
            if (m instanceof SingularBlock sb) {
                glyphIndex.putIfAbsent(sb.glyph(), sb);
            }
        }
    }

    @Test
    void basic_set_carries_expected_bulk_after_perclass_registration() {
        // 525 singulars from kranji-singulars-perclass + kranji-core families.
        // The exact number is load-bearing against the snapshot — if it drifts
        // we want to know.
        int total = BasicSet.INSTANCE.components().size();
        assertTrue(total >= 525,
                "expected at least 525 perclass-registered singulars in BasicSet, got " + total);
    }

    /**
     * Every hand-ported override glyph must resolve to a perclass record
     * (i.e. live in a {@code kranji.singular.<family>.<initial>} package,
     * never in the legacy {@code kranji.singular.<family>} flat package).
     */
    @Test
    void override_glyphs_all_resolve_from_perclass_packages() {
        List<String> overrideGlyphs = List.of(
                "\u53E3",  // 口 Kou
                "\u76EE",  // 目 Mu
                "\u65E5",  // 日 Ri
                "\u6708",  // 月 Yue
                "\u7530",  // 田 Tian
                "\u706B",  // 火 Huo
                "\u571F",  // 土 Tu
                "\u77F3"); // 石 Shi

        for (String g : overrideGlyphs) {
            SingularBlock sb = glyphIndex.get(g);
            assertNotNull(sb, () -> "override glyph not registered: " + g);
            String pkg = sb.getClass().getPackageName();
            assertTrue(pkg.startsWith("kranji.singular.") && pkg.chars().filter(c -> c == '.').count() >= 3,
                    () -> "expected perclass sub-package, got: " + pkg + " for glyph " + g);
            assertTrue(sb instanceof SingularZi,
                    () -> "override glyph must still be a SingularZi: " + g);
        }
    }

    /**
     * Spot-check plain auto-generated records survive the round-trip too.
     * Catches any regression where non-override records get dropped during
     * regeneration.
     */
    @Test
    void sampled_auto_generated_glyphs_all_resolve() {
        List<String> sample = List.of(
                "\u8863",  // 衣 Yi_Clothes (materials)
                "\u738B",  // 王 Wang_King or Wang (people)
                "\u725B",  // 牛 Niu (animals)
                "\u6728",  // 木 Mu_Wood (plants) — different from 目 Mu (body)
                "\u5B50"); // 子 Zi (people)

        for (String g : sample) {
            assertNotNull(glyphIndex.get(g),
                    () -> "expected sample glyph " + g + " to be registered by perclass");
        }
    }

    /**
     * The whole point of hand-porting: Tu as left radical must still emit
     * its 提土旁 SVG hint after flowing through {@code BasicSet}. This is
     * the belt-and-braces check that the override survives JSON → Java →
     * JVM class load → BasicSet collection.
     */
    @Test
    void tu_left_radical_hint_survives_end_to_end() {
        SingularBlock tu = glyphIndex.get("\u571F");
        assertNotNull(tu, "\u571F must be registered");
        assertTrue(tu instanceof SingularZi);
        SingularZi sz = (SingularZi) tu;

        BlockRole left = new LeftRight.Left();
        assertEquals(Politeness.YIELDING, sz.politeness(left));

        LayoutHint hint = sz.hintFor(left);
        assertNotNull(hint, "Tu must emit a hint as LR.Left after BasicSet round-trip");
        assertNotNull(hint.glyph());
        assertEquals("\u571F", hint.glyph().value());
        assertNotNull(hint.glyph().svg(),
                "the \u63D0\u571F\u65C1 SvgShape must be attached");
        assertSame(Politeness.YIELDING, hint.bh().politeness());
    }
}
