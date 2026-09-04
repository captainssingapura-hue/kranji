package kranji.reading.library;

/**
 * Where the reader gets its library.
 *
 * <p>Only the tree. Collections are reachable through it, so there is no second
 * way to enumerate the library and therefore no way for the two to
 * disagree.</p>
 *
 * <p>An implementation is discovered on the classpath, which is what lets
 * content ship independently of the application binary: a collection is built,
 * versioned and released as its own jar, and adding a hundred poems is a
 * content release rather than an application release.</p>
 */
public interface ArticleLibrary {

    /** The whole library, arranged. Leaves are collections. */
    LibraryTree tree();
}
