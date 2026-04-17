package kranji.json.dto;

/**
 * Tagged union returned by {@code ZiCatalog.any(glyph)} / {@code ZiCatalog.all(glyph)}.
 *
 * <p>Query-only type — never serialized to the wire; the three underlying DTOs
 * carry their own Jackson mappings.</p>
 */
public sealed interface EntryJson {

    /** The glyph of the wrapped entry. */
    String glyph();

    record OfSingularZi(SingularZiJson zi) implements EntryJson {
        @Override public String glyph() { return zi.glyph(); }
    }

    record OfSingularPart(SingularPartJson part) implements EntryJson {
        @Override public String glyph() { return part.glyph(); }
    }

    record OfComposedZi(ComposedZiJson zi) implements EntryJson {
        @Override public String glyph() { return zi.glyph(); }
    }
}
