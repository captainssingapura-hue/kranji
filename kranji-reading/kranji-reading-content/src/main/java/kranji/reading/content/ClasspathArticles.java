package kranji.reading.content;

import kranji.reading.model.ArticleId;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

/**
 * The bundled articles, read from {@code /articles} on the classpath.
 *
 * <p>The floor, not the ceiling: it exists so the app has something to read
 * before any authoring tool does, and so the format has a worked example.</p>
 */
public final class ClasspathArticles implements ArticleSource {

    public static final ClasspathArticles INSTANCE = new ClasspathArticles();

    /** Listed rather than scanned — a classpath scan is a surprise waiting to happen. */
    private static final List<String> BUNDLED = List.of("jing-ye-si", "chun-tian", "shi-shi-shi-shi-shi");

    private ClasspathArticles() {}

    @Override
    public List<ArticleId> catalogue() {
        return BUNDLED.stream().map(ArticleId::new).toList();
    }

    @Override
    public Optional<ParsedArticle> find(ArticleId id) {
        String resource = "/articles/" + id.value() + ".txt";
        try (InputStream in = ClasspathArticles.class.getResourceAsStream(resource)) {
            if (in == null) return Optional.empty();
            return Optional.of(ArticleParser.parse(
                    new String(in.readAllBytes(), StandardCharsets.UTF_8)));
        } catch (IOException e) {
            throw new UncheckedIOException("reading " + resource, e);
        }
    }
}
