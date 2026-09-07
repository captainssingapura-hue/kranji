package kranji.reading.library;

import java.util.List;

/**
 * What a shelf lists: an article on its own, or an umbrella over several
 * tellings of one work.
 *
 * <h2>Two cases, and the first one already existed</h2>
 *
 * <p>{@link ArticleRef} <em>is</em> the solo case. It is not wrapped in one:
 * the record every collection already declares its articles with now also
 * implements this interface, and answers {@link #articles()} with itself.
 * That is what makes the change invisible to the 476 articles and six
 * collection classes that predate it — none of them mention this type and
 * none of them need to.</p>
 *
 * <p>{@link ArticleUmbrella} is the new case: one title, and under it a map
 * from a {@link Classifier} to the {@code ArticleRef} that is that telling.
 * 刻舟求剑 is an umbrella whose editions are a retelling and the 吕氏春秋
 * original; 蚂蚁 may one day be an umbrella whose editions are three depths of
 * the same ants.</p>
 *
 * <h2>Addresses do not change</h2>
 *
 * <p>Every telling is still an {@code ArticleRef} with its own
 * {@link LocalId}, and is still addressed {@code collection:local}. The umbrella
 * is how the tree groups them and nothing more; the reader, the party message
 * that names an article, and every bookmark are untouched by it.</p>
 */
public sealed interface ArticleEntry permits ArticleRef, ArticleUmbrella {

    /** Unique among the entries of one collection. */
    LocalId id();

    /** As displayed where the entry is listed. */
    String title();

    /**
     * Every addressable article under this entry, in reading order.
     *
     * <p>One for a solo article; the editions, by rank, for an umbrella. This
     * is what {@link ArticleCollection#articles()} flattens, so everything that
     * resolves an address or counts a library sees tellings and never
     * umbrellas.</p>
     */
    List<ArticleRef> articles();
}
