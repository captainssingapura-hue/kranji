package kranji.reading.library;

import java.util.UUID;

/**
 * Identifies an article or an illustration within its collection.
 *
 * <p>Only locally unique — the collection is the boundary, so two collections
 * may each hold a {@code chun-tian}. A distinct type from {@link CollectionId}
 * so one cannot be passed where the other belongs.</p>
 *
 * <h2>An id is not a filename</h2>
 *
 * <p><b>A local id is immutable once published.</b> The resource may move, the
 * title may be corrected, the author may be fixed. The id may not change.</p>
 *
 * <p>A slug reads far better than a uuid and is what should normally be
 * written — but a slug invites tidying, and progress records, bookmarks and
 * "continue reading" all key on {@code collection:local}. Renaming the file is
 * free; renaming the id is a migration.</p>
 */
public sealed interface LocalId {

    /** The id as it is written down. */
    String value();

    /** A slug: {@code jing-ye-si}. Same lexical rule as a dotted name. */
    record Named(String value) implements LocalId {
        public Named {
            value = Names.require(value, "local id");
        }
        @Override public String toString() { return value; }
    }

    /** A generated id, where nothing readable is available. */
    record Uuid(UUID uuid) implements LocalId {
        public Uuid {
            if (uuid == null) throw new IllegalArgumentException("a local id needs a uuid");
        }
        @Override public String value()    { return uuid.toString(); }
        @Override public String toString() { return uuid.toString(); }
    }

    static LocalId named(String value) { return new Named(value); }

    static LocalId of(UUID uuid) { return new Uuid(uuid); }
}
