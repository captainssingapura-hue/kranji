package kranji.studio.articles;

import hue.captains.singapura.js.homing.core.DomModule;
import hue.captains.singapura.js.homing.core.Exportable;
import hue.captains.singapura.js.homing.core.ExportsOf;
import hue.captains.singapura.js.homing.core.ImportsFor;

import java.util.List;

/**
 * A parsed draft, drawn.
 *
 * <h2>Why this is a module and not part of the pane</h2>
 *
 * <p>Two reasons, and the second is the one that matters. Turning blocks into
 * elements is a dozen small functions, and left inside the widget it would take
 * the pane past the effective-line limit — but more than that, drawing a
 * document is not the pane's subject. The pane's subject is a folder of drafts
 * and which one is being looked at; how a quote or a verse is put on screen has
 * nothing to do with that, and will be answered a second way in stage two.</p>
 *
 * <h2>What it is not</h2>
 *
 * <p>Not the reader's grid. The reader draws practice squares because that is
 * how the characters are written out; stage one draws a document, because the
 * question being asked of it is whether the file says what its author meant.
 * The two renderings share the blocks and nothing else.</p>
 *
 * <p>Everything is minted through the caller's branch. That is the whole point
 * of the rewrite this module came out of: the preview used to be a string of
 * HTML assigned to {@code innerHTML}, which leaves a widget owning none of what
 * it put on screen.</p>
 */
public record MdPreviewModule() implements DomModule<MdPreviewModule> {

    /** Yields {@code draw} and {@code say}. */
    public record createMdPreview() implements Exportable._Constant<MdPreviewModule> {}

    public static final MdPreviewModule INSTANCE = new MdPreviewModule();

    @Override
    public ImportsFor<MdPreviewModule> imports() {
        return ImportsFor.<MdPreviewModule>builder().build();
    }

    @Override
    public ExportsOf<MdPreviewModule> exports() {
        return new ExportsOf<>(INSTANCE, List.of(new createMdPreview()));
    }
}
