package kranji.codegen.depth1;

import kranji.codegen.routing.PinyinRouting;

import java.util.List;

/**
 * Manifest for the depth-1 catalog stored at
 * {@code catalog/depth1/meta.json}. Lets {@link Depth1GenerateMain}
 * enumerate every record without scanning the filesystem.
 *
 * @param depth   depth bucket — always {@code 1} for this catalog
 * @param members member identifiers in {@code "<initial>/<Class>/<FieldName>"}
 *                form (e.g. {@code "m/Ing2/MING"}, {@code "zero/Uo3/WO"});
 *                sorted alphabetically so generated {@code ALL} lists are
 *                deterministic. The {@code <Class>} segment matches the
 *                {@code <Final><Tone>} class name produced by
 *                {@link PinyinRouting#keyFor}, ensuring two records that
 *                share a Java field name (e.g. {@code KONG} = 孔 vs 空)
 *                land in distinct catalog directories.
 */
public record Depth1MetaJson(
        int depth,
        List<String> members
) {}
