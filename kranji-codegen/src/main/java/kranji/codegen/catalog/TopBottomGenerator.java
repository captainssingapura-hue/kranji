package kranji.codegen.catalog;

/** {@code TopBottomT<T, B>} — slots {@code top()}, {@code bottom()}. */
public final class TopBottomGenerator extends BaseStructureGenerator {
    @Override public String layoutCode() { return "TB"; }
    @Override public int arity() { return 2; }
    @Override protected String layoutInterface() { return "TopBottomT"; }
    @Override protected String[] slotAccessorNames() { return new String[]{"top", "bottom"}; }
}
