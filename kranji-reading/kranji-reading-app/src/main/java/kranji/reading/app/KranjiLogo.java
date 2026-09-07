package kranji.reading.app;

import hue.captains.singapura.js.homing.core.ExportsOf;
import hue.captains.singapura.js.homing.core.SvgBeing;
import hue.captains.singapura.js.homing.core.SvgGroup;

import java.util.List;

/**
 * The Kranji brand mark.
 *
 * <p>One being — {@link logo} — which {@link ReadingStudio} hands to
 * {@code StudioBrand}. The group can grow more typed SVG assets later without
 * touching the brand wiring; each one needs only a file at the conventional
 * path {@code homing/svg/<this package>/KranjiLogo/<being simple name>.svg}.</p>
 *
 * <h2>Why a K</h2>
 *
 * <p>Homing's own mark is an H built from three rectangles, and a studio built
 * on Homing that arrives with an unrelated illustration reads as a different
 * product wearing borrowed chrome. The K sits on the same skeleton — same
 * stem, same 18-high box — so the two look like siblings without either
 * having to explain itself.</p>
 *
 * <p>The colour is written into the file rather than taken from
 * {@code --color-accent}. A mark that changes with the theme is not a mark,
 * and with open themes coming the background it sits on is unknowable.</p>
 */
public record KranjiLogo() implements SvgGroup<KranjiLogo> {

    /** The brand glyph: a K on the construction of Homing's H. */
    public record logo() implements SvgBeing<KranjiLogo> {}

    public static final KranjiLogo INSTANCE = new KranjiLogo();

    @Override
    public List<SvgBeing<KranjiLogo>> svgBeings() {
        return List.of(new logo());
    }

    @Override
    public ExportsOf<KranjiLogo> exports() {
        return new ExportsOf<>(this, List.copyOf(svgBeings()));
    }
}
