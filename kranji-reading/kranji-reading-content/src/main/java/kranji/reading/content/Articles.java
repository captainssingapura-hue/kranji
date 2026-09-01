package kranji.reading.content;

import kranji.reading.library.ArticleAddress;
import kranji.reading.library.ArticleCollection;
import kranji.reading.library.ArticleLibrary;
import kranji.reading.library.ArticleRef;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

/**
 * Reads an article's body from wherever its collection says it is.
 *
 * <p>The only place a file is opened. Listing, titling and arranging a library
 * all happen against the Java catalogue; this is reached once, for the article
 * actually being read.</p>
 */
public final class Articles {

    private Articles() {}

    /** The source text an {@link ArticleRef} points at. */
    public static Optional<String> sourceOf(ArticleRef ref) {
        try (InputStream in = Articles.class.getResourceAsStream(ref.resource())) {
            if (in == null) return Optional.empty();
            return Optional.of(new String(in.readAllBytes(), StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new UncheckedIOException("reading " + ref.resource(), e);
        }
    }

    /** Resolves an address against a library and parses what it finds. */
    public static Optional<ParsedArticle> read(ArticleLibrary library, ArticleAddress address) {
        ArticleCollection collection = library.tree().byId().get(address.collection());
        if (collection == null) return Optional.empty();
        return collection.article(address.local()).flatMap(ref -> read(address, ref));
    }

    /** Parses one article, given where it lives. */
    public static Optional<ParsedArticle> read(ArticleAddress address, ArticleRef ref) {
        return sourceOf(ref).map(source -> ArticleParser.parse(address, ref.title(), source));
    }
}
