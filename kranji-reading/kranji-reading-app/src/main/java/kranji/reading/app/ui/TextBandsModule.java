package kranji.reading.app.ui;

import hue.captains.singapura.js.homing.core.DomModule;
import hue.captains.singapura.js.homing.core.Exportable;
import hue.captains.singapura.js.homing.core.ExportsOf;
import hue.captains.singapura.js.homing.core.ImportsFor;

import java.util.List;

/**
 * How many squares fit on a line, and where the line breaks.
 *
 * <p>Chinese breaks between any two characters, so wrapping prose is a matter
 * of choosing a count — there is no hyphenation and no word boundary to
 * respect. The count is <b>quantised into bands</b> rather than fitted exactly,
 * for two reasons: 作文纸 has a fixed number of columns and looks wrong with an
 * arbitrary one, and a banded count only changes when the pane crosses a
 * boundary, so an ordinary resize does not reflow the text under a child's
 * eyes.</p>
 *
 * <p>Pure arithmetic, no DOM — which is why it is a module of its own rather
 * than more lines in the reader.</p>
 */
public record TextBandsModule() implements DomModule<TextBandsModule> {

    /** Yields {@code cellsPerRow(available, cellWidth)} and {@code chunk(cells, n)}. */
    public record createBanding() implements Exportable._Constant<TextBandsModule> {}

    public static final TextBandsModule INSTANCE = new TextBandsModule();

    @Override
    public ImportsFor<TextBandsModule> imports() {
        return ImportsFor.<TextBandsModule>builder().build();
    }

    @Override
    public ExportsOf<TextBandsModule> exports() {
        return new ExportsOf<>(INSTANCE, List.of(new createBanding()));
    }
}
