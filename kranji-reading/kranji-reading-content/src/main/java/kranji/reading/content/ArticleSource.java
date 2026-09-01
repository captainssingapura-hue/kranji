package kranji.reading.content;

import kranji.reading.model.ArticleId;

import java.util.List;
import java.util.Optional;

/**
 * Where articles come from.
 *
 * <p>An SPI so the bundled set is not the only set. A source serves articles
 * to anything — there is no Homing dependency here, and none should appear.</p>
 */
public interface ArticleSource {

    /** Every article this source offers, in the order it wants them listed. */
    List<ArticleId> catalogue();

    /** One article, absent when this source does not have it. */
    Optional<ParsedArticle> find(ArticleId id);
}
