package kranji.library;

import java.util.List;

/**
 * A typed library of Chinese character components.
 *
 * <p>Libraries form an explicit dependency graph: each library declares
 * its dependencies and its components. The application chooses which
 * libraries to load at startup via {@link #load}.</p>
 *
 * <p>Every component ({@link LibraryMember}) is generically bound to exactly one
 * library, enforced at compile time.</p>
 *
 * <p>Example:</p>
 * <pre>
 *   ZiLibrary.load(BasicSet.INSTANCE);
 * </pre>
 */
public interface ZiLibrary {

    /** Human-readable name (e.g. "Basic", "Common 1000"). */
    String name();

    /** Libraries this one depends on (must be loaded first). */
    List<? extends ZiLibrary> dependencies();

    /** All components declared by this library. */
    List<? extends LibraryMember<?>> components();

    /**
     * Load the given libraries, ensuring dependencies are registered
     * before dependents.
     */
    static void load(ZiLibrary... libraries) {
        for (ZiLibrary lib : libraries) {
            lib.register();
        }
    }

    /**
     * Register this library's dependencies then its own components.
     * Implementations must be idempotent.
     */
    void register();
}
