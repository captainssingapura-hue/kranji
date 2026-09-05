package kranji.reading.library;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.ServiceLoader;

/**
 * Every root {@link ArticleLibrary} on the classpath, and the one of them that
 * is actually mounted.
 *
 * <h2>Why this class exists at all</h2>
 *
 * <p>{@code ServiceLoader.load(...)} would be one line. It would also return
 * nothing when a content jar is missing, and the reader would then show an
 * empty shelf while reporting no error — a library with no books and a library
 * that failed to load look identical to a reader. This wraps the loader so that
 * case is loud, and so the answer is settled once rather than at every call
 * site.</p>
 *
 * <h2>One root, chosen the same way every time</h2>
 *
 * <ol>
 *   <li>{@code -D}{@code kranji.reading.library}, if set — an explicit ask
 *       always wins, and naming a root that is not there is an error rather
 *       than a silent fall back to something else.</li>
 *   <li>otherwise the lowest {@link ArticleLibrary#precedence()} present, so a
 *       build that gains real content stops serving the sample without anybody
 *       editing anything.</li>
 *   <li>and if two roots tie at that precedence, nothing is mounted and the tie
 *       is reported, because guessing here means serving one reader's library
 *       to another and never finding out.</li>
 * </ol>
 *
 * <p>Loaded once and held. A ServiceLoader iterates lazily and re-instantiates
 * providers on each pass, so calling it repeatedly would hand out different
 * objects for the same data.</p>
 */
public final class Libraries {

    private Libraries() {}

    /** System property naming the root to mount, by {@link ArticleLibrary#name()}. */
    public static final String LIBRARY_PROPERTY = "kranji.reading.library";

    private static final List<ArticleLibrary> FOUND = load();

    private static List<ArticleLibrary> load() {
        var out = new ArrayList<ArticleLibrary>();
        for (ArticleLibrary l : ServiceLoader.load(ArticleLibrary.class)) out.add(l);
        // Name breaks ties so the order is total: two roots that forgot to
        // differ in precedence still sort into one fixed sequence rather than
        // into whichever the loader felt like. That does not make the tie
        // acceptable - mounted() still refuses it - but it makes the refusal
        // say the same thing on every machine.
        out.sort(Comparator.comparingInt(ArticleLibrary::precedence)
                           .thenComparing(ArticleLibrary::name));
        return List.copyOf(out);
    }

    /**
     * Chosen on first use rather than when this class loads, so that asking
     * what is on the classpath still works on a build that has no content at
     * all — {@link #discovered()} answering "nothing" is more use to a caller
     * than an initialiser error.
     */
    private static final class Chosen {
        static final ArticleLibrary ONE = choose();
    }

    /**
     * What is on the classpath, most preferred first.
     *
     * <p>May be empty. Anything that needs content should use {@link #mounted()}
     * instead, so a missing content jar is an error rather than an empty
     * shelf.</p>
     */
    public static List<ArticleLibrary> discovered() { return FOUND; }

    /**
     * The mounted root, checked.
     *
     * <p>Validated once, here, rather than in every action that reads the tree.
     * A duplicate collection id between two independently released content jars
     * is exactly the fault grafting makes possible, and it should stop start-up
     * rather than surface as the wrong poem.</p>
     */
    public static ArticleLibrary mounted() { return Chosen.ONE; }

    private static ArticleLibrary choose() {
        if (FOUND.isEmpty()) {
            throw new IllegalStateException(
                    "no ArticleLibrary on the classpath - a content module is missing. "
                  + "An empty library and an absent module look identical to a reader, "
                  + "so this refuses rather than showing nothing.");
        }
        ArticleLibrary picked = asked().orElseGet(Libraries::byPrecedence);
        picked.tree().validate();
        return picked;
    }

    private static Optional<ArticleLibrary> asked() {
        String name = System.getProperty(LIBRARY_PROPERTY, "").trim();
        if (name.isEmpty()) return Optional.empty();
        for (ArticleLibrary l : FOUND) {
            if (l.name().equals(name)) return Optional.of(l);
        }
        throw new IllegalStateException(
                LIBRARY_PROPERTY + "='" + name + "' names no library on the classpath; "
              + "found " + names());
    }

    private static ArticleLibrary byPrecedence() {
        ArticleLibrary best = FOUND.get(0);
        if (FOUND.size() > 1 && FOUND.get(1).precedence() == best.precedence()) {
            throw new IllegalStateException(
                    "libraries " + names() + " are all at precedence " + best.precedence()
                  + ", so which one the reader gets would follow the classpath. "
                  + "Set -D" + LIBRARY_PROPERTY + " to one of them, or drop a jar.");
        }
        return best;
    }

    private static String names() {
        return FOUND.stream().map(ArticleLibrary::name).toList().toString();
    }
}
