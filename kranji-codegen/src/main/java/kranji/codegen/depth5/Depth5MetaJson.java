package kranji.codegen.depth5;

import java.util.List;

/**
 * Manifest for the depth-5 catalog stored at
 * {@code catalog/depth5/meta.json}. Lets {@code Depth5GenerateMain}
 * enumerate every record without scanning the filesystem.
 *
 * @param depth   depth bucket — always {@code 5} for this catalog
 * @param members member identifiers in {@code "<initial>/<Class>/<FieldName>"}
 *                form, sorted alphabetically
 */
public record Depth5MetaJson(
        int depth,
        List<String> members
) {}
