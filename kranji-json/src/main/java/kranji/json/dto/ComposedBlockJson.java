package kranji.json.dto;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Wire shape for the spatial composition of a composed character.
 *
 * @param style lowercase-snake style tag (e.g. {@code "left_right"},
 *              {@code "top_bottom"}, {@code "full_enclosure"}); required
 * @param slots slot-key → block-ref map; required; defensively copied into
 *              an order-preserving unmodifiable view so JSON output is
 *              deterministic for callers that pass a {@link LinkedHashMap}.
 *              {@link Map#copyOf} would lose insertion order (it returns
 *              an {@code ImmutableCollections.MapN} whose iteration order
 *              is randomized per JVM run).
 *              Expected key sets per style are defined in the loader validator.
 */
public record ComposedBlockJson(
        String style,
        Map<String, BlockRefJson> slots
) {
    public ComposedBlockJson {
        slots = slots == null
                ? Collections.emptyMap()
                : Collections.unmodifiableMap(new LinkedHashMap<>(slots));
    }
}
