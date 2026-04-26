package kranji.codegen.catalog;

/** {@code SemiEnclosureBottomLeftT<W, C>} — slots {@code wrapper()}, {@code content()}. Used for 辶, 走 etc. */
public final class SemiEnclosureBottomLeftGenerator extends BaseStructureGenerator {
    @Override public String layoutCode() { return "SemiBL"; }
    @Override public int arity() { return 2; }
    @Override protected String layoutInterface() { return "SemiEnclosureBottomLeftT"; }
    @Override protected String[] slotAccessorNames() { return new String[]{"wrapper", "content"}; }
}
