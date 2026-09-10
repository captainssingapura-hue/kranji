package kranji.studio.articles;

import hue.captains.singapura.js.homing.core.DomModule;
import hue.captains.singapura.js.homing.core.Exportable;
import hue.captains.singapura.js.homing.core.ExportsOf;
import hue.captains.singapura.js.homing.core.ImportsFor;

import java.util.List;

/**
 * The Secretary for the article workspace's shelf.
 *
 * <p>Three panes and one conversation: a root is chosen, and then a draft
 * inside it. {@code PickRoot} and {@code PickDraft} go in;
 * {@code ShelfChanged} comes back out to everybody.</p>
 *
 * <p>One bus rather than two, for the reason the gloss workbench gives next
 * door: two parties would mean two declarations and two joins per widget, and
 * a widget that forgot one would go quiet with nothing to show for it. What
 * changed is a field on the message, so a pane reacts to the half it cares
 * about.</p>
 *
 * <p>Every broadcast carries the whole state. A pane opened after a root was
 * chosen is then correct on the next change rather than needing a resync
 * handshake — the same reason the gloss bus carries its whole selection.</p>
 *
 * <p><b>Choosing a root clears the draft.</b> Draft ids are relative to their
 * root, so an id from one folder means nothing in another and would resolve to
 * either nothing or, far worse, a different file that happens to sit at the
 * same relative path.</p>
 */
public record ArticleShelfSecretaryModule()
        implements DomModule<ArticleShelfSecretaryModule> {

    /** The pure (state, envelope) → step function. */
    public record ArticleShelfSecretary()
            implements Exportable._Constant<ArticleShelfSecretaryModule> {}

    public static final ArticleShelfSecretaryModule INSTANCE =
            new ArticleShelfSecretaryModule();

    @Override
    public ImportsFor<ArticleShelfSecretaryModule> imports() {
        return ImportsFor.<ArticleShelfSecretaryModule>builder().build();
    }

    @Override
    public ExportsOf<ArticleShelfSecretaryModule> exports() {
        return new ExportsOf<>(INSTANCE, List.of(new ArticleShelfSecretary()));
    }
}
