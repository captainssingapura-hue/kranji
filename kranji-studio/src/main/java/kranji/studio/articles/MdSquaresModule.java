package kranji.studio.articles;

import hue.captains.singapura.js.homing.core.DomModule;
import hue.captains.singapura.js.homing.core.Exportable;
import hue.captains.singapura.js.homing.core.ExportsOf;
import hue.captains.singapura.js.homing.core.ImportsFor;

import java.util.List;

/**
 * A {@link GridPlan}, drawn as the page of squares it describes.
 *
 * <h2>The second of two views on one response</h2>
 *
 * <p>{@link MdPreviewModule} draws the document — what the file says.
 * This draws the arrangement — what the reader will do with it. They come from
 * the same fetch and the same draft, which is what makes the bench a bench: the
 * two questions are asked side by side.</p>
 *
 * <h2>Divs, not RelationGrid</h2>
 *
 * <p>Deliberately, and only for now. Stage two exists to settle the sizes, and
 * a plain grid keeps that separate from whether the grid substrate can draw
 * them — the substrate cannot, yet, which is exactly the thing being worked
 * around. Stage three swaps it and the plan does not change.</p>
 */
public record MdSquaresModule() implements DomModule<MdSquaresModule> {

    /** Yields {@code draw}. */
    public record createMdSquares() implements Exportable._Constant<MdSquaresModule> {}

    public static final MdSquaresModule INSTANCE = new MdSquaresModule();

    @Override
    public ImportsFor<MdSquaresModule> imports() {
        return ImportsFor.<MdSquaresModule>builder().build();
    }

    @Override
    public ExportsOf<MdSquaresModule> exports() {
        return new ExportsOf<>(INSTANCE, List.of(new createMdSquares()));
    }
}
