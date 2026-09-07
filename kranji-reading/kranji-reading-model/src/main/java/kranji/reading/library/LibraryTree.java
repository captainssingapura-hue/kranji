package kranji.reading.library;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * How collections are arranged for a reader to browse.
 *
 * <p>Nodes are written by whoever composes the library; leaves are whole
 * collections. Composition is ordinary Java, so a school, a family and a
 * publisher can each arrange the same collection jars differently without any
 * of them rebuilding the reader.</p>
 *
 * <pre>{@code
 * LibraryTree.of("Reading",
 *     LibraryTree.branch("诗歌", TangShi.INSTANCE, ErGe.INSTANCE),
 *     LibraryTree.branch("故事", YuYan.INSTANCE, ChengYu.INSTANCE));
 * }</pre>
 */
public sealed interface LibraryTree {

    /** As displayed. */
    String title();

    /** A named node holding further nodes or collections. */
    record Branch(String title, List<LibraryTree> children) implements LibraryTree {
        public Branch {
            if (title == null || title.isBlank()) {
                throw new IllegalArgumentException("a branch needs a title");
            }
            children = List.copyOf(children);
        }
    }

    /** A whole collection, mounted. */
    record Shelf(ArticleCollection collection) implements LibraryTree {
        public Shelf {
            if (collection == null) throw new IllegalArgumentException("a shelf needs a collection");
        }
        @Override public String title() { return collection.title(); }
    }

    // ── Composition ────────────────────────────────────────────────────

    static LibraryTree of(String title, LibraryTree... children) {
        return new Branch(title, List.of(children));
    }

    /** A node holding collections directly — the common shape. */
    static LibraryTree branch(String title, ArticleCollection... collections) {
        var shelves = new ArrayList<LibraryTree>();
        for (ArticleCollection c : collections) shelves.add(new Shelf(c));
        return new Branch(title, shelves);
    }

    static LibraryTree shelf(ArticleCollection collection) {
        return new Shelf(collection);
    }

    // ── Reading the tree ───────────────────────────────────────────────

    /** Every collection in the tree, in the order it is mounted. */
    default List<ArticleCollection> collections() {
        var out = new ArrayList<ArticleCollection>();
        gather(this, out);
        return List.copyOf(out);
    }

    private static void gather(LibraryTree node, List<ArticleCollection> out) {
        switch (node) {
            case Shelf s -> out.add(s.collection());
            case Branch b -> {
                for (LibraryTree child : b.children()) gather(child, out);
            }
        }
    }

    /**
     * Indexes the tree's collections by id.
     *
     * <p><b>Fails on a collision, naming both offenders.</b> A duplicate
     * collection in a library is the kind of fault nobody notices for months,
     * and by then the wrong one is the one being read — so it stops start-up
     * rather than resolving to whichever jar happened to load first.</p>
     */
    default Map<CollectionId, ArticleCollection> byId() {
        var out = new LinkedHashMap<CollectionId, ArticleCollection>();
        for (ArticleCollection c : collections()) {
            ArticleCollection clash = out.putIfAbsent(c.id(), c);
            if (clash != null && clash != c) {
                throw new IllegalStateException(
                        "two collections claim the id '" + c.id() + "': "
                        + clash.getClass().getName() + " and " + c.getClass().getName());
            }
        }
        return out;
    }

    /** Resolves an address against this tree. */
    default Optional<ArticleRef> find(ArticleAddress address) {
        ArticleCollection c = byId().get(address.collection());
        return c == null ? Optional.empty() : c.article(address.local());
    }

    /**
     * Checks the whole tree at once: collection ids unique, and local ids
     * unique within each collection. Called at start-up.
     *
     * @throws IllegalStateException naming what collided
     */
    default void validate() {
        byId();
        for (ArticleCollection c : collections()) {
            var seen = new HashMap<LocalId, String>();
            for (ArticleRef a : c.articles()) {
                String clash = seen.putIfAbsent(a.id(), a.title());
                if (clash != null) {
                    throw new IllegalStateException("collection '" + c.id()
                            + "' has two articles with the id '" + a.id() + "': "
                            + clash + " and " + a.title());
                }
            }
            // Entries are the tree's nodes, so their ids must be unique too -
            // separately from the articles', because an umbrella and the
            // telling it opens on may legitimately share a slug.
            var seenEntries = new HashMap<LocalId, String>();
            for (ArticleEntry e : c.entries()) {
                String clash = seenEntries.putIfAbsent(e.id(), e.title());
                if (clash != null) {
                    throw new IllegalStateException("collection '" + c.id()
                            + "' lists two entries with the id '" + e.id() + "': "
                            + clash + " and " + e.title());
                }
            }
            // The flat list and the listed entries must be the same articles.
            // A collection that overrides entries() and forgets articles()
            // would resolve addresses for tellings the tree never shows.
            var flattened = new ArrayList<ArticleRef>();
            for (ArticleEntry e : c.entries()) flattened.addAll(e.articles());
            if (!flattened.equals(c.articles())) {
                throw new IllegalStateException("collection '" + c.id()
                        + "' lists " + flattened.size() + " article(s) through its entries but "
                        + c.articles().size() + " through articles(); they must agree");
            }
            var seenImages = new HashMap<LocalId, String>();
            for (ImageRef i : c.illustrations()) {
                String clash = seenImages.putIfAbsent(i.id(), i.alt());
                if (clash != null) {
                    throw new IllegalStateException("collection '" + c.id()
                            + "' has two illustrations with the id '" + i.id() + "'");
                }
            }
        }
    }
}
