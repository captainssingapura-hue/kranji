package kranji.reading.model;

import java.util.ArrayList;
import java.util.List;

/**
 * The articles, shelved by class.
 *
 * <p>Two levels and no more: a class, and the articles in it. Deeper nesting
 * would be a taxonomy rather than a shelf, and nothing yet needs one.</p>
 *
 * <p>A {@link Title} carries the whole header rather than only the id, because
 * a tree has to be drawn as well as navigated — the label is the title and the
 * note is the author. What travels when a title is chosen is just the id.</p>
 */
public sealed interface ArticleTree {

    /** One class of article, with the articles catalogued under it. */
    record Shelf(ArticleClass type, List<Title> titles) implements ArticleTree {
        public Shelf {
            titles = List.copyOf(titles);
        }
    }

    /** One article on a shelf. */
    record Title(ArticleHeader header) implements ArticleTree {

        /** What travels on the party when this title is chosen. */
        public ArticleId id() { return header.id(); }
    }

    /**
     * Shelves the given headers, in the declared order of {@link ArticleClass}.
     *
     * <p>A class with no article is left out rather than shown empty — an
     * empty shelf in a reading app reads as something being broken.</p>
     */
    static List<Shelf> shelve(List<ArticleHeader> headers) {
        var shelves = new ArrayList<Shelf>();
        for (ArticleClass type : ArticleClass.values()) {
            var titles = new ArrayList<Title>();
            for (ArticleHeader header : headers) {
                if (header.type() == type) titles.add(new Title(header));
            }
            if (!titles.isEmpty()) shelves.add(new Shelf(type, titles));
        }
        return List.copyOf(shelves);
    }
}
