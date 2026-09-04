package kranji.reading.app.known;

import hue.captains.singapura.js.homing.core.DomModule;
import hue.captains.singapura.js.homing.core.Exportable;
import hue.captains.singapura.js.homing.core.ExportsOf;
import hue.captains.singapura.js.homing.core.ImportsFor;

import java.util.List;

/**
 * The Import and Undo controls, and what happens when a file arrives.
 *
 * <p>Reading a file is not making a claim. Mark Known is about one character's
 * readings laid side by side so that <em>this one, not that one</em> can be
 * chosen honestly; this is about a text file arriving from a parent's downloads
 * folder. They shared a widget because they share a bar, and the widget had
 * grown past the point where both fitted — the same reason
 * {@code ReaderControlsModule} exists.</p>
 *
 * <p>It owns its three controls and nothing else. The note line stays with the
 * pane, because the pane writes there too and two owners of one element is how
 * a message about a failed import gets wiped by an unrelated click.</p>
 */
public record KnownImportControlsModule() implements DomModule<KnownImportControlsModule> {

    /** Builds the controls and returns {@code { controls, showUndo, onUndo }}. */
    public record createKnownImportControls()
            implements Exportable._Constant<KnownImportControlsModule> {}

    public static final KnownImportControlsModule INSTANCE = new KnownImportControlsModule();

    @Override
    public ImportsFor<KnownImportControlsModule> imports() {
        return ImportsFor.<KnownImportControlsModule>builder().build();
    }

    @Override
    public ExportsOf<KnownImportControlsModule> exports() {
        return new ExportsOf<>(INSTANCE, List.of(new createKnownImportControls()));
    }
}
