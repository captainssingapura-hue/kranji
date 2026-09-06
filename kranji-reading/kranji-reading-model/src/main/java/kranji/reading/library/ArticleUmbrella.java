package kranji.reading.library;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * One work, several tellings.
 *
 * <p>The umbrella has a title and an id of its own — it is a node the tree
 * draws — and a map from {@link Classifier} to the {@link ArticleRef} that is
 * that telling. It holds no text and no resource itself: it is not something
 * a reader opens, it is the thing a reader opens <em>one of</em>.</p>
 *
 * <h2>Typed on one classifier family</h2>
 *
 * <p>{@code C} is a family from {@link Classifier} — {@code Depth} or
 * {@code Provenance}. An umbrella of retellings cannot be handed a
 * {@code Level}, because a level of a retelling is not the same axis as a
 * level of an explanation, and the map's key type is what says so.</p>
 *
 * <h2>Order is the classifier's, not the author's</h2>
 *
 * <p>The map is re-sorted by {@link Classifier#rank()} on construction and
 * iterates in that order. A collection can list the original first because
 * that is how the source reads; the reader still meets the retelling first,
 * because that is how a child reads.</p>
 *
 * @param id       unique among the collection's entries
 * @param title    the work's name — 刻舟求剑 — under which the editions hang
 * @param editions at least one; keyed by what distinguishes each telling
 */
public record ArticleUmbrella<C extends Classifier>(LocalId id, String title,
                                                    Map<C, ArticleRef> editions)
        implements ArticleEntry {

    public ArticleUmbrella {
        if (id == null) throw new IllegalArgumentException("an umbrella needs an id");
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("umbrella '" + id + "' needs a title");
        }
        if (editions == null || editions.isEmpty()) {
            throw new IllegalArgumentException("umbrella '" + id + "' has no editions");
        }
        var ordered = new LinkedHashMap<C, ArticleRef>();
        editions.entrySet().stream()
                .sorted(Comparator.comparingInt(e -> e.getKey().rank()))
                .forEach(e -> ordered.put(e.getKey(), e.getValue()));
        editions = Collections.unmodifiableMap(ordered);
    }

    /** The editions, by rank. */
    @Override
    public List<ArticleRef> articles() {
        return List.copyOf(new ArrayList<>(editions.values()));
    }

    /** The telling under one classifier, absent when this work has none. */
    public Optional<ArticleRef> edition(C classifier) {
        return Optional.ofNullable(editions.get(classifier));
    }

    /** Convenience: a slug id. */
    public static <C extends Classifier> ArticleUmbrella<C> of(String slug, String title,
                                                              Map<C, ArticleRef> editions) {
        return new ArticleUmbrella<>(LocalId.named(slug), title, editions);
    }
}
