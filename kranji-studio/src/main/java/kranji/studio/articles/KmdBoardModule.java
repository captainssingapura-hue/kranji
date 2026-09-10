package kranji.studio.articles;

import hue.captains.singapura.js.homing.core.DomModule;
import hue.captains.singapura.js.homing.core.Exportable;
import hue.captains.singapura.js.homing.core.ExportsOf;
import hue.captains.singapura.js.homing.core.ImportsFor;
import hue.captains.singapura.js.homing.core.ModuleImports;
import hue.captains.singapura.js.homing.grid.RelationGridModule;

import java.util.List;

/**
 * A {@link GridPlan}, drawn on the real grid.
 *
 * <h2>Stage three</h2>
 *
 * <p>{@link MdSquaresModule} draws a plan as plain divs and says in its own
 * header that it does so only until the substrate can take it: <i>stage two
 * exists to settle the sizes, and stage three swaps the grid without the plan
 * changing.</i> This is that swap, and the plan did not change — which is the
 * claim that note was making and this is the test of.</p>
 *
 * <h2>What the grid brings that divs did not</h2>
 *
 * <p>A cursor. {@code RelationGrid} owns arrow-key movement over cells, and
 * reports where it is through {@code onCursorMoved} — so a reader walks the
 * page with the keyboard and something above the sheet can follow along without
 * either of them knowing about the other.</p>
 *
 * <p>That is what makes a one-square run readable. The square shows a
 * placeholder because a word does not fit in a box; the cursor says which
 * placeholder is under the reader; and the strip says what it stands for. None
 * of the three needs to be clever because the grid already tracks the one thing
 * that ties them together.</p>
 *
 * <h2>Its own relation, rather than the reader's</h2>
 *
 * <p>The reading app has {@code createLineRelation} doing the same thirty lines
 * over {@code .txt} cells. Borrowing it would put a cross-repository module
 * import in the way of a read-only adapter that is a {@code get} and four
 * no-ops, and would tie this to a cell shape that is not this one's. The
 * duplication is smaller than the coupling.</p>
 */
public record KmdBoardModule() implements DomModule<KmdBoardModule> {

    /** Yields {@code build}. */
    public record createKmdBoard() implements Exportable._Constant<KmdBoardModule> {}

    public static final KmdBoardModule INSTANCE = new KmdBoardModule();

    @Override
    public ImportsFor<KmdBoardModule> imports() {
        return ImportsFor.<KmdBoardModule>builder()
                .add(new ModuleImports<>(
                        List.of(new RelationGridModule.RelationGrid()),
                        RelationGridModule.INSTANCE))
                .build();
    }

    @Override
    public ExportsOf<KmdBoardModule> exports() {
        return new ExportsOf<>(INSTANCE, List.of(new createKmdBoard()));
    }
}
