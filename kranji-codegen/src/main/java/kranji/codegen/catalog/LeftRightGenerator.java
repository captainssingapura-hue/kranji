package kranji.codegen.catalog;

/** {@code LeftRightT<L, R>} — slots {@code left()}, {@code right()}. */
public final class LeftRightGenerator extends BaseStructureGenerator {
    @Override public String layoutCode() { return "LR"; }
    @Override public int arity() { return 2; }
    @Override protected String layoutInterface() { return "LeftRightT"; }
    @Override protected String[] slotAccessorNames() { return new String[]{"left", "right"}; }
}
