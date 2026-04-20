package kranji.codegen.depth3;

import java.util.List;

/**
 * Manifest for the depth-3 catalog stored at
 * {@code catalog/depth3/meta.json}. Lets {@code Depth3GenerateMain}
 * enumerate every record without scanning the filesystem.
 *
 * @param depth   depth bucket — always {@code 3} for this catalog
 * @param members member identifiers in {@code "<initial>/<Class>/<FieldName>"}
 *                form, sorted alphabetically
 */
public record Depth3MetaJson(
        int depth,
        List<String> members
) {}
