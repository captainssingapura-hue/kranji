package kranji.reading.model;

import java.util.UUID;

/**
 * What an article says about itself, before its body is read.
 *
 * <p>Enough to list, classify, and address an article without parsing it. That
 * separation is the point: a catalogue of three thousand articles must not cost
 * three thousand full parses — resolving every character's reading against the
 * corpus and validating every override — to answer what they are called.</p>
 *
 * <h2>The uuid is the article's identity</h2>
 *
 * <p>Not its {@link ArticleId} slug, and emphatically not its position in the
 * catalogue. An article can be reclassified, retitled, or mounted in several
 * places at once; the uuid does not move with it. The slug stays as the
 * human-readable handle used in URLs and file names — a second identity, and
 * one that may legitimately be corrected.</p>
 *
 * @param id     the slug, matching the file name
 * @param uuid   the identity, authored in the header and never regenerated
 * @param title  the title as it is displayed
 * @param author who wrote it, empty when anonymous or traditional
 * @param type   the class it is catalogued under
 */
public record ArticleHeader(ArticleId id,
                            UUID uuid,
                            String title,
                            String author,
                            ArticleClass type) {

    public ArticleHeader {
        if (id == null)    throw new IllegalArgumentException("an article header needs an id");
        if (uuid == null)  throw new IllegalArgumentException("an article header needs a uuid");
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("an article header needs a title");
        }
        if (type == null)  throw new IllegalArgumentException("an article header needs a type");
        author = author == null ? "" : author.trim();
    }

    /** True when an author is recorded. */
    public boolean hasAuthor() { return !author.isEmpty(); }
}
