package kranji.codegen.catalog;

/** {@code LeftMiddleRightT<L, M, R>} — slots {@code left()}, {@code middle()}, {@code right()}. */
public final class LeftMiddleRightGenerator extends BaseStructureGenerator {
    @Override public String layoutCode() { return "LR3"; }
    @Override public int arity() { return 3; }
    @Override protected String layoutInterface() { return "LeftMiddleRightT"; }
    @Override protected String[] slotAccessorNames() {
        return new String[]{"left", "middle", "right"};
    }
}
