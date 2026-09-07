package kranji.reading.app;

import hue.captains.singapura.js.homing.core.SvgRef;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * The brand mark is where the header says it is.
 *
 * <p>{@code SvgRef.resolve()} returns empty when the file is not on the
 * classpath and the studio quietly draws its default coloured dot instead.
 * That is the right behaviour for a framework and the wrong failure for us:
 * a misspelt package, a resource directory the build does not copy, or a
 * class moved to a new package all produce an app that looks fine to
 * everything except a person who knows there should be a K there.</p>
 *
 * <p>The path is derived from the group's package and class names, so it
 * changes silently when either does. This is the test that notices.</p>
 */
class KranjiLogoTest {

    private static final SvgRef<KranjiLogo> LOGO =
            new SvgRef<>(KranjiLogo.INSTANCE, new KranjiLogo.logo());

    @Test
    void theMarkResolvesFromTheClasspath() {
        Optional<String> svg = LOGO.resolve();

        assertTrue(svg.isPresent(),
                "no brand mark at " + LOGO.resourcePath()
              + " - the studio will fall back to its default dot without saying so");
        assertTrue(svg.get().contains("<svg"), "resolved, but not as SVG");
    }

    @Test
    void theMarkIsOnHomingsConstruction() {
        String svg = LOGO.resolve().orElseThrow();

        // The same 24x24 box the framework's own H uses. A mark drawn to a
        // different viewBox lines up differently in the header strip, which
        // is the kind of thing nobody notices until both are on screen.
        assertTrue(svg.contains("viewBox=\"0 0 24 24\""), "not on the 24x24 box");
        // And the same stem: x=4, 18 high, starting at y=3.
        assertTrue(svg.contains("x=\"4\" y=\"3\" width=\"4\" height=\"18\""),
                "the K's stem no longer sits where the H's does");
    }

    @Test
    void theColourIsWrittenDownRatherThanTokenised() {
        String svg = LOGO.resolve().orElseThrow();

        // Deliberate, and the reason is open themes: the mark has to hold on
        // a background nobody has written yet. A var() here would make the
        // logo a function of whichever theme is loaded.
        assertTrue(svg.contains("#2F9E4F"), "the Kranji green is not in the mark");
        assertEquals(-1, svg.indexOf("var(--"),
                "the mark must not take its colour from a theme token");
    }

    @Test
    void theStudioActuallyHandsItOver() {
        // The mark existing and the mark being wired are different facts, and
        // the second one is what puts it on the screen.
        var brand = ReadingStudio.INSTANCE.standaloneBrand();

        assertEquals("Kranji · Reading", brand.label());
        assertTrue(brand.logo() != null, "the studio brand carries no logo");
        assertEquals(LOGO.resourcePath(), brand.logo().resourcePath());
    }
}
