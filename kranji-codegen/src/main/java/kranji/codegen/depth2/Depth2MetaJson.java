package kranji.codegen.depth2;

import kranji.codegen.routing.PinyinRouting;

import java.util.List;

/**
 * Manifest for the depth-2 catalog stored at
 * {@code catalog/depth2/meta.json}. Lets {@code Depth2GenerateMain}
 * enumerate every record without scanning the filesystem.
 *
 * @param depth   depth bucket — always {@code 2} for this catalog
 * @param members member identifiers in {@code "<initial>/<Class>/<FieldName>"}
 *                form, sorted alphabetically. The {@code <Class>} segment
 *                matches the {@code <Final><Tone>} class name produced by
 *                {@link PinyinRouting#keyFor} (the routing rules are
 *                depth-agnostic).
 */
public record Depth2MetaJson(
        int depth,
        List<String> members
) {}
