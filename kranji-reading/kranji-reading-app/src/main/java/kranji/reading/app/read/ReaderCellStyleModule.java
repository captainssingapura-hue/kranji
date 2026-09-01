package kranji.reading.app.read;

import hue.captains.singapura.js.homing.core.DomModule;
import hue.captains.singapura.js.homing.core.Exportable;
import hue.captains.singapura.js.homing.core.ExportsOf;
import hue.captains.singapura.js.homing.core.ImportsFor;
import hue.captains.singapura.js.homing.core.ModuleImports;
import kranji.reading.app.css.ReadingCss;

import java.util.List;

/**
 * How a square is dressed.
 *
 * <p>A cell's classes are assembled rather than enumerated — a base, a size, a
 * typeface, and whatever else that cell needs — and rebuilt whole on every
 * change, because {@code setClass} replaces rather than adds.</p>
 *
 * <p>Punctuation is drawn by the cell's own class rather than by an element, so
 * nothing can widen the square. How far a mark is pushed depends on how much of
 * its box the character in front of it fills, measured from actual ink and
 * quantised into four buckets: a class per character would be unbounded, and
 * four steps is below what the eye resolves at this size.</p>
 *
 * <p>Split out of the reader when it crossed the 250 effective-line limit for
 * the second time. Dressing a cell and deciding what a cell contains are
 * different jobs, and the limit is what made that obvious.</p>
 */
public record ReaderCellStyleModule() implements DomModule<ReaderCellStyleModule> {

    /** Yields {@code cell(td, fontSpec)}, {@code ann(td)}, {@code fontSpecOf(td)}. */
    public record createReaderCellStyle() implements Exportable._Constant<ReaderCellStyleModule> {}

    /** Yields {@code table(db, key, cells)} - one line as a two-row table. */
    public record createReaderLine() implements Exportable._Constant<ReaderCellStyleModule> {}

    public static final ReaderCellStyleModule INSTANCE = new ReaderCellStyleModule();

    /**
     * The cell classes are imported here rather than passed in. They are used
     * nowhere else now, and holding them here keeps the reader from importing
     * ten class handles only to hand them straight back.
     */
    @Override
    public ImportsFor<ReaderCellStyleModule> imports() {
        return ImportsFor.<ReaderCellStyleModule>builder()
                .add(new ModuleImports<>(List.of(
                        new ReadingCss.kr_read_zi(),
                        new ReadingCss.kr_read_ann(),
                        new ReadingCss.kr_read_grid(),
                        new ReadingCss.kr_read_hidden(),
                        new ReadingCss.kr_read_punct(),
                        new ReadingCss.kr_read_punct_lead(),
                        new ReadingCss.kr_read_punct_x0(),
                        new ReadingCss.kr_read_punct_x1(),
                        new ReadingCss.kr_read_punct_x2(),
                        new ReadingCss.kr_read_punct_x3(),
                        new ReadingCss.kr_read_line()),
                        ReadingCss.INSTANCE))
                .build();
    }

    @Override
    public ExportsOf<ReaderCellStyleModule> exports() {
        return new ExportsOf<>(INSTANCE,
                List.of(new createReaderCellStyle(), new createReaderLine()));
    }
}
