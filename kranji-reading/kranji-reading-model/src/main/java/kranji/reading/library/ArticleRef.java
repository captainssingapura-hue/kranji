package kranji.reading.library;

/**
 * An article, as the catalogue knows it.
 *
 * <p>A value, not a type. There is no class per article and no {@code INSTANCE}
 * — a collection is a list of these, one line each.</p>
 *
 * <p><b>Metadata is Java; the body is a resource.</b> Listing therefore never
 * parses: a catalogue of three thousand articles is three thousand records
 * already in memory, not three thousand files opened to read a title. That is
 * the property that makes the scale workable, and the reason none of this lives
 * in the article's own text.</p>
 *
 * @param id       unique within the collection, immutable once published
 * @param title    as displayed
 * @param author   empty when anonymous or traditional
 * @param resource classpath path to the article's source text
 */
public record ArticleRef(LocalId id, String title, String author, String resource) {

    public ArticleRef {
        if (id == null) throw new IllegalArgumentException("an article needs an id");
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("article '" + id + "' needs a title");
        }
        if (resource == null || resource.isBlank()) {
            throw new IllegalArgumentException("article '" + id + "' needs a resource");
        }
        author = author == null ? "" : author.trim();
    }

    /** Convenience for the common case: a slug id and no author. */
    public static ArticleRef of(String slug, String title, String resource) {
        return new ArticleRef(LocalId.named(slug), title, "", resource);
    }

    /** Convenience: a slug id with an author. */
    public static ArticleRef by(String slug, String title, String author, String resource) {
        return new ArticleRef(LocalId.named(slug), title, author, resource);
    }

    public boolean hasAuthor() { return !author.isEmpty(); }
}
