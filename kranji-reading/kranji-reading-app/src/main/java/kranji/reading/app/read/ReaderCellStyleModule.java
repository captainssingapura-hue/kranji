package kranji.reading.app.read;

import hue.captains.singapura.js.homing.core.DomModule;
import hue.captains.singapura.js.homing.core.Exportable;
import hue.captains.singapura.js.homing.core.ExportsOf;
import hue.captains.singapura.js.homing.core.ImportsFor;
import hue.captains.singapura.js.homing.core.ModuleImports;
import kranji.reading.app.css.ReadingCss;

import java.util.List;

/**
 * Where a punctuation mark sits in its square.
 *
 * <p>Punctuation is drawn by the square's own class rather than by an element,
 * so nothing can widen the square. How far a mark is pushed depends on how much
 * of its box the character in front of it fills, measured from actual ink and
 * quantised into four buckets: a class per character would be unbounded, and
 * four steps is below what the eye resolves at this size.</p>
 *
 * <p>Dressing a square is {@link ArticleBoardModule}'s job now. What is left
 * here is the measurement, which needs a laid-out element and so cannot happen
 * at paint time.</p>
 */
public record ReaderCellStyleModule() implements DomModule<ReaderCellStyleModule> {

    /** Yields {@code offsetClass(ratio)} and {@code fontSpecOf(el)}. */
    public record createReaderCellStyle() implements Exportable._Constant<ReaderCellStyleModule> {}

    public static final ReaderCellStyleModule INSTANCE = new ReaderCellStyleModule();

    /** Only the four offset buckets are still needed here. */
    @Override
    public ImportsFor<ReaderCellStyleModule> imports() {
        return ImportsFor.<ReaderCellStyleModule>builder()
                .add(new ModuleImports<>(List.of(
                        new ReadingCss.kr_read_punct_x0(),
                        new ReadingCss.kr_read_punct_x1(),
                        new ReadingCss.kr_read_punct_x2(),
                        new ReadingCss.kr_read_punct_x3()),
                        ReadingCss.INSTANCE))
                .build();
    }

    @Override
    public ExportsOf<ReaderCellStyleModule> exports() {
        return new ExportsOf<>(INSTANCE, List.of(new createReaderCellStyle()));
    }
}
