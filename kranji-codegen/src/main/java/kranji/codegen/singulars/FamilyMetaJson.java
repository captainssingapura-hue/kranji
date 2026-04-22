package kranji.codegen.singulars;

import java.util.List;

/**
 * Per-family manifest stored as {@code catalog/<family>/meta.json}. Lets
 * {@code GenerateMain} discover each family's members (and whether they
 * are Zi or radical-parts) without filesystem scanning.
 *
 * @param family  sub-directory name under {@code catalog/}, e.g.
 *                {@code "materials"} or {@code "radicals"}
 * @param kind    {@code "zi"} for {@link kranji.zi.SingularZi} records,
 *                {@code "part"} for {@link kranji.zi.SingularPart} records
 * @param members class simple names of every entry in this family,
 *                alphabetically sorted so {@code ALL} lists are
 *                deterministic
 */
public record FamilyMetaJson(
        String family,
        String kind,
        List<String> members
) {
    public static final String KIND_ZI   = "zi";
    public static final String KIND_PART = "part";
}
