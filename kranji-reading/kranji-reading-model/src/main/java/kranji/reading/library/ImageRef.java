package kranji.reading.library;

/**
 * An illustration, as the catalogue knows it.
 *
 * <p>Provided by the same collection as the articles that use it, and
 * identified the same way. A collection is therefore self-contained: it can be
 * reviewed, licensed and shipped without reaching for assets held elsewhere.</p>
 *
 * @param id       unique within the collection, immutable once published
 * @param alt      described for a reader who cannot see it
 * @param resource classpath path to the image bytes
 */
public record ImageRef(LocalId id, String alt, String resource) {

    public ImageRef {
        if (id == null) throw new IllegalArgumentException("an illustration needs an id");
        if (alt == null || alt.isBlank()) {
            // Not optional. An unlabelled image is unusable to part of the
            // audience, and the omission is invisible to everyone else.
            throw new IllegalArgumentException("illustration '" + id + "' needs alt text");
        }
        if (resource == null || resource.isBlank()) {
            throw new IllegalArgumentException("illustration '" + id + "' needs a resource");
        }
    }

    public static ImageRef of(String slug, String alt, String resource) {
        return new ImageRef(LocalId.named(slug), alt, resource);
    }
}
