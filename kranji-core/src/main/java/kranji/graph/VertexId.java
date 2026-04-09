package kranji.graph;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * A tagged, human-legible vertex identifier of the form {@code "tag:naturalKey"}.
 *
 * <p>The tag disambiguates across vertex families (e.g. {@code "char:清"} vs {@code "sem:氵"}).</p>
 *
 * @param tag the vertex family tag
 * @param key the natural key within that family
 */
public record VertexId(String tag, String key) {

    /** Serializes as the compact {@code "tag:key"} string. */
    @JsonValue
    @Override
    public String toString() {
        return tag + ":" + key;
    }

    /** Parses a VertexId from its {@code "tag:key"} string form. */
    @JsonCreator
    public static VertexId parse(String id) {
        int colon = id.indexOf(':');
        if (colon < 0) throw new IllegalArgumentException("Invalid VertexId (no colon): " + id);
        return new VertexId(id.substring(0, colon), id.substring(colon + 1));
    }

    // Factory methods for each vertex family

    public static VertexId ofChar(String glyph)          { return new VertexId("char", glyph); }
    public static VertexId ofPart(String glyph)          { return new VertexId("part", glyph); }
    public static VertexId ofPinyin(String numberedTone)  { return new VertexId("py", numberedTone); }
    public static VertexId ofInitial(String initial)      { return new VertexId("ini", initial); }
    public static VertexId ofFinal(String fin)            { return new VertexId("fin", fin); }
    public static VertexId ofPhonetic(String glyph)       { return new VertexId("phon", glyph); }
    public static VertexId ofSemantic(String glyph)       { return new VertexId("sem", glyph); }
    public static VertexId ofField(String fieldName)      { return new VertexId("fld", fieldName); }
}
