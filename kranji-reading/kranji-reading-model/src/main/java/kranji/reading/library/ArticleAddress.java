package kranji.reading.library;

/**
 * Where a thing is, anywhere in the library.
 *
 * <p>The pair is the identity. It is already independent of where the
 * collection is mounted, which is why nothing carries a separate uuid: moving a
 * collection to a different place in the tree, or into a different tree
 * entirely, does not change a single address.</p>
 *
 * <pre>{@code kranji.reader.demo.tangshi:jing-ye-si}</pre>
 */
public record ArticleAddress(CollectionId collection, LocalId local) {

    public ArticleAddress {
        if (collection == null) throw new IllegalArgumentException("an address needs a collection");
        if (local == null)      throw new IllegalArgumentException("an address needs a local id");
    }

    /** The written form, {@code collection:local}. */
    @Override
    public String toString() {
        return collection.value() + ":" + local.value();
    }
}
