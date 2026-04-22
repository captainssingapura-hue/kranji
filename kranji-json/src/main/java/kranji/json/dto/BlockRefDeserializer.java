package kranji.json.dto;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

/**
 * Branches on token shape:
 * <ul>
 *   <li>{@code VALUE_STRING} → {@link BlockRefJson.GlyphRef}</li>
 *   <li>{@code START_OBJECT} with a {@code style} field → {@link BlockRefJson.InlineComposed}</li>
 *   <li>{@code START_OBJECT} without a {@code style} field → {@link BlockRefJson.InlineSingular}</li>
 *   <li>{@code VALUE_NULL} → {@code null}</li>
 * </ul>
 *
 * <p>The class holds no state; Jackson may instantiate it once per mapper.</p>
 */
public final class BlockRefDeserializer extends JsonDeserializer<BlockRefJson> {

    @Override
    public BlockRefJson deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonToken token = p.currentToken();
        if (token == JsonToken.VALUE_NULL) {
            return null;
        }
        if (token == JsonToken.VALUE_STRING) {
            return new BlockRefJson.GlyphRef(p.getValueAsString());
        }
        if (token == JsonToken.START_OBJECT) {
            JsonNode node = p.readValueAsTree();
            if (node.has("style")) {
                ComposedBlockJson block = ctxt.readTreeAsValue(node, ComposedBlockJson.class);
                return new BlockRefJson.InlineComposed(block);
            }
            SingularPartJson part = ctxt.readTreeAsValue(node, SingularPartJson.class);
            return new BlockRefJson.InlineSingular(part);
        }
        return (BlockRefJson) ctxt.handleUnexpectedToken(BlockRefJson.class, p);
    }
}
