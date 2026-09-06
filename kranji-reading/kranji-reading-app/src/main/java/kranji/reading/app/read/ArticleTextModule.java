package kranji.reading.app.read;

import hue.captains.singapura.js.homing.core.Exportable;
import hue.captains.singapura.js.homing.core.ExportsOf;
import hue.captains.singapura.js.homing.core.ImportsFor;
import hue.captains.singapura.js.homing.core.DomModule;

import java.util.List;

/**
 * An article's text, in the shapes the reader draws from.
 *
 * <p>Three questions with the same subject and none of them about a pane:
 * which lines an article is made of, what squares a line becomes, and how a
 * paragraph's first line is set in.</p>
 *
 * <p>They were closures inside {@link ArticleReaderWidget}, which four things
 * reached into — the board, the title, the readability line and the loader.
 * Out here they are one object with one owner, and the widget is back under
 * its line budget, which is what the limit is for.</p>
 *
 * <p>No DOM, so the answers can be checked under GraalVM with no browser. The
 * scan rule belongs to {@link ArticleScannerModule} and the readings to
 * {@link ArticleReadingsModule}; both arrive injected rather than imported, so
 * a test can hand in a stub.</p>
 */
public record ArticleTextModule() implements DomModule<ArticleTextModule> {

    /** Yields {@code cellsOf}, {@code indented} and {@code textsOf}. */
    public record createArticleText() implements Exportable._Constant<ArticleTextModule> {}

    public static final ArticleTextModule INSTANCE = new ArticleTextModule();

    @Override
    public ImportsFor<ArticleTextModule> imports() {
        return ImportsFor.<ArticleTextModule>builder().build();
    }

    @Override
    public ExportsOf<ArticleTextModule> exports() {
        return new ExportsOf<>(INSTANCE, List.of(new createArticleText()));
    }
}
