package kranji.reading.app.zi;

import hue.captains.singapura.js.homing.core.DomModule;
import hue.captains.singapura.js.homing.core.Exportable;
import hue.captains.singapura.js.homing.core.ExportsOf;
import hue.captains.singapura.js.homing.core.ImportsFor;

import java.util.List;

/**
 * The readings of one character, as a grid — and the cell that claims one.
 *
 * <h2>A row is the claim</h2>
 *
 * <p>Its primary key is {@code codepoint:reading}, which is the key the known
 * set stores, the census counts and the gloss tier files under. Marking a row
 * therefore needs no lookup and composes nothing: the row already <em>is</em>
 * the thing that would be written.</p>
 *
 * <h2>Why the toggle is one-way</h2>
 *
 * <p>Claiming is idempotent and giving back is not. A grid cursor lands on rows
 * by arrow key and by stray click, so a cell that could unmark would put the
 * destruction of a deliberate claim one press away from wherever the cursor
 * happened to be. The cell claims; nothing in a cell takes a claim back.</p>
 *
 * <h2>Why a module and not more lines in the widget</h2>
 *
 * <p>The same reason {@code ReaderControlsModule} exists: the pane is about one
 * character, and a grid with a custom cell type is a body of behaviour that has
 * nothing to do with which character is selected. It also keeps the cell's
 * contract — render, update, dispose, and the edit trio it deliberately
 * refuses — in one readable place.</p>
 */
public record ZiReadingsGridModule() implements DomModule<ZiReadingsGridModule> {

    /** Builds the grid; returns {@code { show, refresh, destroy }}. */
    public record createZiReadingsGrid() implements Exportable._Constant<ZiReadingsGridModule> {}

    public static final ZiReadingsGridModule INSTANCE = new ZiReadingsGridModule();

    @Override
    public ImportsFor<ZiReadingsGridModule> imports() {
        return ImportsFor.<ZiReadingsGridModule>builder().build();
    }

    @Override
    public ExportsOf<ZiReadingsGridModule> exports() {
        return new ExportsOf<>(INSTANCE, List.of(new createZiReadingsGrid()));
    }
}
