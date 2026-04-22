package kranji.codegen.perclass;

import java.nio.file.Path;
import java.util.Locale;

/**
 * Pure routing helpers for the per-class typed codegen.
 *
 * <p>Translates the committed catalog's on-disk shape
 * ({@code catalog/depth<N>/<initial>/<FinalTone>/<FIELD_NAME>.json}) into
 * the target Java layout
 * ({@code kranji.common.perclass.<initial>.<finaltone>.<ClassName>}).</p>
 *
 * <p>All methods are pure functions of their arguments — no IO, no state.
 * This keeps the emitter deterministic: running it twice on the same
 * catalog produces byte-identical output.</p>
 */
public final class PerclassRouting {

    /** Java package root for all typed per-class records. */
    public static final String ROOT_PACKAGE = "kranji.common.perclass";

    /** Catalog root relative to the codegen module. */
    public static final Path CATALOG_ROOT =
            Path.of("kranji-codegen", "src", "main", "resources", "catalog");

    /** Output root (where emitted .java files go). */
    public static final Path OUTPUT_ROOT =
            Path.of("kranji-common-perclass", "src", "main", "java",
                    "kranji", "common", "perclass");

    private PerclassRouting() {}

    /**
     * One record's routing key, derived from its catalog file path.
     *
     * @param initial      first segment, already lowercase (e.g. {@code "n"},
     *                     {@code "zh"}, {@code "zero"})
     * @param finalTone    second segment, lowercased from the PascalCase
     *                     catalog directory name (e.g. {@code "i3"}, {@code "er3"})
     * @param fieldName    JSON file stem — original Java field identifier
     *                     (e.g. {@code "NI_YOU"}, {@code "MING"})
     * @param className    PascalCase of {@code fieldName}
     *                     (e.g. {@code "NiYou"}, {@code "Ming"})
     */
    public record Key(String initial, String finalTone,
                      String fieldName, String className) {

        /** Package of the emitted record, e.g. {@code kranji.common.perclass.n.i3}. */
        public String packageName() {
            return ROOT_PACKAGE + "." + initial + "." + finalTone;
        }

        /** Fully-qualified class name, e.g. {@code kranji.common.perclass.n.i3.NiYou}. */
        public String fqn() {
            return packageName() + "." + className;
        }
    }

    /**
     * Compute the routing key for a catalog-relative path such as
     * {@code depth2/n/I3/NI_YOU.json}.
     */
    public static Key keyFor(Path catalogRelativePath) {
        // Normalise to forward slashes for uniform splitting.
        String rel = catalogRelativePath.toString().replace('\\', '/');
        String[] parts = rel.split("/");
        if (parts.length != 4) {
            throw new IllegalArgumentException(
                    "Expected depth<N>/<initial>/<FinalTone>/<FIELD>.json, got: " + rel);
        }
        String initial   = parts[1];                                // already lowercase
        String finalTone = parts[2].toLowerCase(Locale.ROOT);       // PascalCase → lowercase
        String jsonName  = parts[3];
        if (!jsonName.endsWith(".json")) {
            throw new IllegalArgumentException("Not a .json file: " + rel);
        }
        String fieldName = jsonName.substring(0, jsonName.length() - 5);
        String className = pascalCase(fieldName);
        return new Key(initial, finalTone, fieldName, className);
    }

    /**
     * {@code "MING"} → {@code "Ming"}, {@code "NI_YOU"} → {@code "NiYou"},
     * {@code "ER_YOU_ARCHAIC"} → {@code "ErYouArchaic"}.
     */
    public static String pascalCase(String snakeUpper) {
        StringBuilder sb = new StringBuilder(snakeUpper.length());
        boolean capsNext = true;
        for (int i = 0; i < snakeUpper.length(); i++) {
            char c = snakeUpper.charAt(i);
            if (c == '_') { capsNext = true; continue; }
            if (capsNext) {
                sb.append(Character.toUpperCase(c));
                capsNext = false;
            } else {
                sb.append(Character.toLowerCase(c));
            }
        }
        return sb.toString();
    }
}
