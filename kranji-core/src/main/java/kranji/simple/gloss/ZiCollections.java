package kranji.simple.gloss;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ServiceLoader;

/**
 * Every {@link ZiCollection} on the classpath, and a refusal to be quiet about
 * finding none.
 *
 * <h2>Why this class exists at all</h2>
 *
 * <p>{@code ServiceLoader.load(...)} would be one line. It would also return an
 * empty stream when a module is missing, and every consumer would then show a
 * reader nothing while reporting no error — which is what g5 rejected discovery
 * for. This wraps the loader so that case is loud, and so the answer is
 * composed once rather than at every call site.</p>
 *
 * <p>Loaded once and held. A ServiceLoader iterates lazily and re-instantiates
 * providers on each pass, so calling it repeatedly would hand out different
 * objects for the same data.</p>
 */
public final class ZiCollections {

    private ZiCollections() {}

    private static final List<ZiCollection> FOUND = load();

    private static List<ZiCollection> load() {
        var out = new ArrayList<ZiCollection>();
        for (ZiCollection c : ServiceLoader.load(ZiCollection.class)) out.add(c);
        // Sorted, because Glosses.of keeps the first entry it sees for a
        // character and ServiceLoader does not specify what order it hands
        // them over - that follows the classpath, which differs between a jar,
        // an IDE and a shaded build. Unsorted, a character glossed both by
        // hand and by machine would show whichever the classpath happened to
        // yield, and the same build could answer differently elsewhere.
        //
        // Name breaks ties so the order is total: two collections at the same
        // precedence still load in one fixed order rather than in whichever
        // one the loader felt like.
        out.sort(Comparator.comparingInt(ZiCollection::precedence)
                           .thenComparing(ZiCollection::name));
        return List.copyOf(out);
    }

    /**
     * What is on the classpath, in discovery order.
     *
     * <p>May be empty, and a caller that can sensibly do nothing should use
     * this. Anything that needs data should use {@link #required()} instead, so
     * a missing module is an error rather than an empty screen.</p>
     */
    public static List<ZiCollection> discovered() { return FOUND; }

    /**
     * The same, but empty is a failure.
     *
     * <p>The one line that turns discovery's silent mode into a loud one. A
     * build without a gloss module is a legitimate build; a build that expects
     * glosses and silently has none is the failure g5 named.</p>
     */
    public static List<ZiCollection> required() {
        if (FOUND.isEmpty()) {
            throw new IllegalStateException(
                    "no ZiCollection on the classpath - a gloss module is missing. "
                  + "An empty set and an absent module look identical to a reader, "
                  + "so this refuses rather than showing nothing.");
        }
        return FOUND;
    }

    /** Every discovered collection composed into one registry. */
    public static Glosses glosses() {
        return Glosses.of(required().toArray(GlossSource[]::new));
    }

    /** Every discovered collection's phrases, composed. */
    @SuppressWarnings("unchecked")
    public static Examples phrases() {
        var lists = required().stream().map(c -> c.phrases().all()).toList();
        return Examples.of(lists.toArray(List[]::new));
    }
}
