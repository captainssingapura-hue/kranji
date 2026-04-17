package kranji.json.dto;

import java.util.Map;

/**
 * Wire shape for the spatial composition of a composed character.
 *
 * @param style lowercase-snake style tag (e.g. {@code "left_right"},
 *              {@code "top_bottom"}, {@code "full_enclosure"}); required
 * @param slots slot-key → block-ref map; required; defensively copied.
 *              Expected key sets per style are defined in the loader validator.
 */
public record ComposedBlockJson(
        String style,
        Map<String, BlockRefJson> slots
) {
    public ComposedBlockJson {
        slots = slots == null ? Map.of() : Map.copyOf(slots);
    }
}
