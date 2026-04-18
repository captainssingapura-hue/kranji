package kranji.codegen.singulars;

/**
 * Reads the JSON catalog at
 * {@code kranji-singulars-perclass/src/main/resources/catalog/} and emits
 * one Java record class per entry into
 * {@code kranji-singulars-perclass/src/main/java/kranji/singular/...}.
 *
 * <p>Skips files whose first line starts with
 * {@code // PARTIALLY GENERATED} to preserve hand-ported overrides.</p>
 *
 * <p>Implementation lands in a follow-up commit.</p>
 */
public final class GenerateMain {
    private GenerateMain() {}

    public static void main(String[] args) {
        throw new UnsupportedOperationException("GenerateMain not implemented yet");
    }
}
