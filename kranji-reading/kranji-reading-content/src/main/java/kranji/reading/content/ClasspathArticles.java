package kranji.reading.content;

import kranji.reading.model.ArticleClass;
import kranji.reading.model.ArticleHeader;
import kranji.reading.model.ArticleId;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The bundled articles, read from {@code /articles} on the classpath.
 *
 * <p>The floor, not the ceiling: it exists so the app has something to read
 * before any authoring tool does, and so the format has a worked example.</p>
 *
 * <h2>Listing does not parse</h2>
 *
 * <p>{@link #headers()} reads only what precedes {@code ---}, so building a
 * catalogue costs one small read per article rather than a full parse of each.
 * {@link #find} is the expensive call and is made once, for the article
 * actually being read.</p>
 */
public final class ClasspathArticles implements ArticleSource {

    public static final ClasspathArticles INSTANCE = new ClasspathArticles();

    /** Listed rather than scanned — a classpath scan is a surprise waiting to happen. */
    private static final List<String> BUNDLED = List.of(
            "jing-ye-si",
            "chun-tian",
            "shi-shi-shi-shi-shi",
            "shou-zhu-dai-tu",
            "hua-she-tian-zu",
            "xiao-yu-dian",
            "xiong-mao");

    /** Headers are immutable and read from the jar; reading them twice is waste. */
    private volatile List<ArticleHeader> cached;

    private ClasspathArticles() {}

    @Override
    public List<ArticleId> catalogue() {
        return headers().stream().map(ArticleHeader::id).toList();
    }

    /**
     * Every bundled article's header, in declaration order.
     *
     * @throws IllegalArgumentException naming the file, when a header is wrong
     */
    public List<ArticleHeader> headers() {
        List<ArticleHeader> local = cached;
        if (local != null) return local;

        var out = new ArrayList<ArticleHeader>();
        for (String slug : BUNDLED) {
            String source = sourceOf(slug).orElseThrow(() ->
                    new IllegalStateException("bundled article is missing: " + slug + ".txt"));
            try {
                out.add(ArticleParser.headerOnly(source));
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException(
                        "articles/" + slug + ".txt: " + e.getMessage(), e);
            }
        }
        cached = List.copyOf(out);
        return cached;
    }

    /** The articles of one class, in declaration order. */
    public List<ArticleHeader> of(ArticleClass type) {
        return headers().stream().filter(h -> h.type() == type).toList();
    }

    /** One article's header. */
    public Optional<ArticleHeader> headerOf(ArticleId id) {
        return headers().stream().filter(h -> h.id().equals(id)).findFirst();
    }

    /** The article's source text, exactly as authored. */
    public Optional<String> sourceOf(ArticleId id) {
        return sourceOf(id.value());
    }

    private Optional<String> sourceOf(String slug) {
        String resource = "/articles/" + slug + ".txt";
        try (InputStream in = ClasspathArticles.class.getResourceAsStream(resource)) {
            if (in == null) return Optional.empty();
            return Optional.of(new String(in.readAllBytes(), StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new UncheckedIOException("reading " + resource, e);
        }
    }

    @Override
    public Optional<ParsedArticle> find(ArticleId id) {
        return sourceOf(id).map(ArticleParser::parse);
    }
}
