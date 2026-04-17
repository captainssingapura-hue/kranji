package kranji.json.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * A reference occupying one slot of a {@link ComposedBlockJson}, or one side
 * of a {@link EtymologyJson.PhonoSemantic}.
 *
 * <p>Three wire forms:</p>
 * <ul>
 *   <li><b>String</b> — a glyph reference (shorthand, most compact).
 *       Deserializes to {@link GlyphRef}.</li>
 *   <li><b>Object with {@code style}</b> — a nested composition.
 *       Deserializes to {@link InlineComposed}.</li>
 *   <li><b>Object without {@code style}</b> — a full singular part payload.
 *       Deserializes to {@link InlineSingular}.</li>
 * </ul>
 */
@JsonDeserialize(using = BlockRefDeserializer.class)
@JsonSerialize(using = BlockRefSerializer.class)
public sealed interface BlockRefJson {

    /** A glyph-string reference; resolution is the caller's responsibility. */
    record GlyphRef(String glyph) implements BlockRefJson {}

    /** A fully inlined singular part (not a registry reference). */
    record InlineSingular(SingularPartJson part) implements BlockRefJson {}

    /** A fully inlined composed block (recursive). */
    record InlineComposed(ComposedBlockJson block) implements BlockRefJson {}
}
