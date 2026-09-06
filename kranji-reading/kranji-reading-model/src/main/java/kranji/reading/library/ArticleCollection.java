package kranji.reading.library;

import java.util.List;
import java.util.Optional;

/**
 * An atomic set of articles and the illustrations they use.
 *
 * <p>A collection is the unit of everything: authorship, review, licensing,
 * versioning and release. A jar of Tang poems is reviewed as a jar of Tang
 * poems.</p>
 *
 * <p>It is also the unit of <b>mounting</b>. A collection goes into a tree
 * whole, and an individual article is never attached to a tree. If articles
 * could be mounted one at a time the tree would become a second description of
 * the library — one that has to be kept in step with the collections and that
 * rots silently when it is not. As it stands, rearranging a tree cannot lose or
 * duplicate an article, because the tree never held one.</p>
 */
public interface ArticleCollection {

    /** Globally unique. Two collections claiming this is an error at start-up. */
    CollectionId id();

    /** As displayed where the collection is mounted. */
    String title();

    /** Why a reader would open this set rather than another. */
    default String summary() { return ""; }

    /**
     * Every addressable article, in the order the collection wants them read.
     *
     * <p>Flat: an umbrella's editions appear here as themselves, one after
     * another, and the umbrella does not. This is what address resolution, the
     * catalogue checks and the article count all walk, and none of them need
     * to know that two of these are tellings of one work.</p>
     */
    List<ArticleRef> articles();

    /**
     * What the shelf lists, in order: solo articles and umbrellas.
     *
     * <p>Defaults to {@link #articles()} unchanged — every {@link ArticleRef}
     * is already an {@link ArticleEntry} — so a collection that has never heard
     * of umbrellas lists exactly what it always did. A collection that groups
     * tellings under one name overrides this and derives {@code articles()}
     * from it, which is the direction that cannot let the two disagree.</p>
     */
    default List<? extends ArticleEntry> entries() { return articles(); }

    /** The illustrations these articles use. */
    default List<ImageRef> illustrations() { return List.of(); }

    /** One article, absent when this collection does not hold it. */
    default Optional<ArticleRef> article(LocalId id) {
        return articles().stream().filter(a -> a.id().equals(id)).findFirst();
    }

    /** One illustration, absent when this collection does not hold it. */
    default Optional<ImageRef> illustration(LocalId id) {
        return illustrations().stream().filter(i -> i.id().equals(id)).findFirst();
    }

    /** The full address of one of this collection's articles. */
    default ArticleAddress address(LocalId local) {
        return new ArticleAddress(id(), local);
    }
}
