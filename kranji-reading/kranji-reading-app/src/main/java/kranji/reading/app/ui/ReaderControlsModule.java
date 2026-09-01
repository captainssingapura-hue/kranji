package kranji.reading.app.ui;

import hue.captains.singapura.js.homing.core.DomModule;
import hue.captains.singapura.js.homing.core.Exportable;
import hue.captains.singapura.js.homing.core.ExportsOf;
import hue.captains.singapura.js.homing.core.ImportsFor;

import java.util.List;

/**
 * The reader's control bar: which article, how much pinyin, how big, and
 * whether the practice grid is drawn.
 *
 * <p>Building controls is not reading, and the reader had grown past the point
 * where both fitted in one module. This owns the widgets and what they
 * remember; the reader asks it what is currently chosen.</p>
 *
 * <p>The typeface picker is passed in already built rather than composed here,
 * so the two stay independent — the Characters pane wants the picker without
 * any of this.</p>
 */
public record ReaderControlsModule() implements DomModule<ReaderControlsModule> {

    /** Builds the bar and reports the current selections. */
    public record createReaderControls() implements Exportable._Constant<ReaderControlsModule> {}

    public static final ReaderControlsModule INSTANCE = new ReaderControlsModule();

    @Override
    public ImportsFor<ReaderControlsModule> imports() {
        return ImportsFor.<ReaderControlsModule>builder().build();
    }

    @Override
    public ExportsOf<ReaderControlsModule> exports() {
        return new ExportsOf<>(INSTANCE, List.of(new createReaderControls()));
    }
}
