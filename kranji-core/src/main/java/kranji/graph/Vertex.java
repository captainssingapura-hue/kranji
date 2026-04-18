package kranji.graph;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import kranji.zi.BlockStructure;
import kranji.classification.EtymologicalCategory;
import kranji.pinyin.Final;
import kranji.pinyin.Initial;
import kranji.pinyin.PinyinSyllable;

/**
 * A vertex in the character navigation graph.
 *
 * <p>Eight variants split into two families:</p>
 * <ul>
 *   <li><b>Leaf vertices</b> (spokes): {@link PartVertex}, {@link CharVertex}</li>
 *   <li><b>Hub vertices</b>: {@link PinyinHub}, {@link InitialHub}, {@link FinalHub},
 *       {@link PhoneticHub}, {@link SemanticHub}, {@link SemanticFieldHub}</li>
 * </ul>
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Vertex.PartVertex.class, name = "part_vertex"),
        @JsonSubTypes.Type(value = Vertex.CharVertex.class, name = "char_vertex"),
        @JsonSubTypes.Type(value = Vertex.PinyinHub.class, name = "pinyin_hub"),
        @JsonSubTypes.Type(value = Vertex.InitialHub.class, name = "initial_hub"),
        @JsonSubTypes.Type(value = Vertex.FinalHub.class, name = "final_hub"),
        @JsonSubTypes.Type(value = Vertex.PhoneticHub.class, name = "phonetic_hub"),
        @JsonSubTypes.Type(value = Vertex.SemanticHub.class, name = "semantic_hub"),
        @JsonSubTypes.Type(value = Vertex.SemanticFieldHub.class, name = "semantic_field_hub")
})
public sealed interface Vertex {

    VertexId id();

    // ── Leaf vertices (spokes) ──────────────────────────────────────────

    /** 偏旁 vertex — a dependent component. */
    record PartVertex(
            VertexId id,
            String glyph,
            String name,
            int strokes
    ) implements Vertex {}

    /** 字 vertex — a standalone character. */
    record CharVertex(
            VertexId id,
            String glyph,
            PinyinSyllable pinyin,
            int strokes,
            int radicalNo,
            BlockStructure structure,
            EtymologicalCategory etymology
    ) implements Vertex {}

    // ── Hub vertices ────────────────────────────────────────────────────

    /** Exact syllable + tone hub (e.g. qīng). */
    record PinyinHub(
            VertexId id,
            PinyinSyllable syllable
    ) implements Vertex {}

    /** Onset consonant hub / 声母 (e.g. all characters starting with "q-"). */
    record InitialHub(
            VertexId id,
            Initial initial
    ) implements Vertex {}

    /** Rhyme group hub / 韵母 (e.g. all characters ending in "-ing"). */
    record FinalHub(
            VertexId id,
            Final fin
    ) implements Vertex {}

    /** Shared phonetic radical hub / 声旁 (e.g. 青 family). */
    record PhoneticHub(
            VertexId id,
            String glyph,
            String soundHint
    ) implements Vertex {}

    /** Shared semantic radical hub / 形旁 (e.g. 氵 water radical). */
    record SemanticHub(
            VertexId id,
            String glyph,
            String meaningHint
    ) implements Vertex {}

    /** Broad meaning domain hub (e.g. "water"). */
    record SemanticFieldHub(
            VertexId id,
            String field
    ) implements Vertex {}
}
