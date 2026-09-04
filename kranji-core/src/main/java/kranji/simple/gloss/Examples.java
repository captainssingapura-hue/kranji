package kranji.simple.gloss;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * Every phrase used as an example, defined once.
 *
 * <p>A phrase belongs to no character in particular - 银行 teaches 银 and 行
 * equally - so it lives here and the glosses reference it. Written once,
 * referenced from wherever it is useful, which is both the saving and the
 * reverse index: asking which characters a word teaches is reading its key.</p>
 *
 * <p>Composed explicitly, like {@link Glosses}: no discovery, no boot step, and
 * a build without a source is a compile-time fact rather than a silent gap.</p>
 */
public final class Examples {

    private final Map<EgKey, ExampleEntry> byKey;
    /** Authoring order, which the map does not keep - see {@link #all()}. */
    private final List<ExampleEntry> ordered;
    private final List<ExampleEntry> shadowed;

    private Examples(Map<EgKey, ExampleEntry> byKey, List<ExampleEntry> ordered,
                     List<ExampleEntry> shadowed) {
        this.ordered = ordered;
        this.byKey = byKey;
        this.shadowed = shadowed;
    }

    /** Composes the entries given, earlier ones winning on a repeated phrase. */
    public static Examples of(List<ExampleEntry>... sources) {
        Objects.requireNonNull(sources, "sources");
        var byKey = new LinkedHashMap<EgKey, ExampleEntry>();
        var shadowed = new ArrayList<ExampleEntry>();
        for (List<ExampleEntry> source : sources) {
            for (ExampleEntry entry : source) {
                ExampleEntry prior = byKey.putIfAbsent(entry.key(), entry);
                if (prior != null && !prior.equals(entry)) shadowed.add(entry);
            }
        }
        return new Examples(Map.copyOf(byKey), List.copyOf(byKey.values()),
                            List.copyOf(shadowed));
    }

    public static Examples none() { return new Examples(Map.of(), List.of(), List.of()); }

    /** What this phrase means, if it has been defined. */
    public Optional<ExampleEntry> find(EgKey key) {
        return Optional.ofNullable(byKey.get(Objects.requireNonNull(key, "key")));
    }

    public int size() { return byKey.size(); }

    /**
     * Every phrase, <b>in the order it was authored</b>.
     *
     * <p>Not {@code byKey.values()}. The map is built insertion-ordered and
     * then frozen with {@code Map.copyOf}, whose iteration order is explicitly
     * unspecified - so reading the values back gives an arbitrary order that
     * looks stable until the data changes. The order is authored information
     * here, the same way a sense's rank is, so it is kept deliberately.</p>
     */
    public List<ExampleEntry> all() { return ordered; }

    /**
     * Phrases a later source defined differently from an earlier one.
     *
     * <p>An identical repeat is absorbed - saying the same thing twice is not a
     * disagreement. Two glosses of one phrase is, and it is kept so somebody
     * can look.</p>
     */
    public List<ExampleEntry> shadowed() { return shadowed; }
}
