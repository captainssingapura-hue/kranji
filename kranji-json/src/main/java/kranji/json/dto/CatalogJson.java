package kranji.json.dto;

import java.util.List;

/**
 * Top-level wire shape: a catalog of singular Zi, singular parts, and composed Zi.
 *
 * @param version        schema version tag; current value: {@code "1"}
 * @param singularZi     standalone characters (defensively copied, never null)
 * @param singularParts  radical variants / 偏旁 (defensively copied, never null)
 * @param composedZi     composed characters (defensively copied, never null)
 */
public record CatalogJson(
        String version,
        List<SingularZiJson> singularZi,
        List<SingularPartJson> singularParts,
        List<ComposedZiJson> composedZi
) {
    public CatalogJson {
        singularZi    = singularZi    == null ? List.of() : List.copyOf(singularZi);
        singularParts = singularParts == null ? List.of() : List.copyOf(singularParts);
        composedZi    = composedZi    == null ? List.of() : List.copyOf(composedZi);
    }
}
