package kranji.reading.app.read;

import hue.captains.singapura.js.homing.core.DomModule;
import hue.captains.singapura.js.homing.core.Exportable;
import hue.captains.singapura.js.homing.core.ExportsOf;
import hue.captains.singapura.js.homing.core.ImportsFor;

import java.util.List;

/**
 * The Secretary for the article-selection Party.
 *
 * <p>A third bus, separate from navigation and from character selection. The
 * navigator relays tree <em>positions</em>: every widget joined to it reads an
 * incoming {@code NavigateTo} as a path. An article is not a path — it is an
 * identity — and the reader wants that identity with nothing to resolve.</p>
 *
 * <p>Keeping it apart also keeps the catalogue from being the only possible
 * producer. A "continue reading" tile, a search result, or a link in another
 * article can all say {@code ArticleSelected} without knowing the tree
 * exists.</p>
 *
 * <p>Just a redirect: an incoming {@code ArticleSelected} is rebroadcast to
 * every member as {@code ShowArticle}. The producer never names its
 * consumer.</p>
 *
 * <h2>State shape</h2>
 * <pre>{@code
 * {
 *     lastSelected : "jing-ye-si" | null,
 *     recentUnknown: [{ kind, from }]     // bounded at 10
 * }
 * }</pre>
 */
public record ArticleSelectionSecretaryModule()
        implements DomModule<ArticleSelectionSecretaryModule> {

    /** The single export — a JS object with {@code initial} and {@code behavior}. */
    public record ArticleSelectionSecretary()
            implements Exportable._Constant<ArticleSelectionSecretaryModule> {}

    public static final ArticleSelectionSecretaryModule INSTANCE =
            new ArticleSelectionSecretaryModule();

    @Override
    public ImportsFor<ArticleSelectionSecretaryModule> imports() {
        return ImportsFor.<ArticleSelectionSecretaryModule>builder().build();
    }

    @Override
    public ExportsOf<ArticleSelectionSecretaryModule> exports() {
        return new ExportsOf<>(INSTANCE, List.of(new ArticleSelectionSecretary()));
    }
}
