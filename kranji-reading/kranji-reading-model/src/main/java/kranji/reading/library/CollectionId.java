package kranji.reading.library;

import java.util.UUID;

/**
 * Identifies a collection, globally.
 *
 * <p>A type rather than a {@code String}. A raw string id is the kind of thing
 * that gets passed where a different id was meant and fails at run time — in a
 * library, where that failure is a child reading the wrong text.</p>
 *
 * <p>Sealed so a third form can arrive without changing a signature or
 * invalidating anything already stored.</p>
 */
public sealed interface CollectionId {

    /** The id as it is written down. */
    String value();

    /**
     * A dotted, package-like name — the normal choice for anything curated.
     *
     * <pre>{@code kranji.reader.demo.tangshi}</pre>
     *
     * <p>The dotted form carries ownership the way a package name does: we own
     * {@code kranji.*}, and anyone shipping a collection takes a prefix they
     * control. That is the whole naming authority — there is no registry, and
     * none is wanted.</p>
     */
    record Named(String value) implements CollectionId {
        public Named {
            value = Names.require(value, "collection id");
        }
        @Override public String toString() { return value; }
    }

    /**
     * A generated id, for where no naming authority exists — an import, a
     * machine-made set, a third party with no prefix of its own.
     */
    record Uuid(UUID uuid) implements CollectionId {
        public Uuid {
            if (uuid == null) throw new IllegalArgumentException("a collection id needs a uuid");
        }
        @Override public String value()    { return uuid.toString(); }
        @Override public String toString() { return uuid.toString(); }
    }

    /** Parses the dotted form. */
    static CollectionId named(String value) { return new Named(value); }

    /** Wraps a generated id. */
    static CollectionId of(UUID uuid) { return new Uuid(uuid); }
}
