package kranji.json.dto;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * Writes a {@link BlockRefJson} in its most compact wire form:
 * <ul>
 *   <li>{@link BlockRefJson.GlyphRef}        → a plain string</li>
 *   <li>{@link BlockRefJson.InlineSingular}  → the inner {@code SingularPartJson} object</li>
 *   <li>{@link BlockRefJson.InlineComposed}  → the inner {@code ComposedBlockJson} object</li>
 * </ul>
 *
 * <p>Stateless.</p>
 */
public final class BlockRefSerializer extends JsonSerializer<BlockRefJson> {

    @Override
    public void serialize(BlockRefJson value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        switch (value) {
            case BlockRefJson.GlyphRef g       -> gen.writeString(g.glyph());
            case BlockRefJson.InlineSingular s -> serializers.defaultSerializeValue(s.part(), gen);
            case BlockRefJson.InlineComposed c -> serializers.defaultSerializeValue(c.block(), gen);
        }
    }
}
