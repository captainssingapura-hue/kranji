package kranji.codegen.catalog;

/** {@code SemiEnclosureUpperRightT<W, C>} — slots {@code wrapper()}, {@code content()}. */
public final class SemiEnclosureUpperRightGenerator extends BaseStructureGenerator {
    @Override public String layoutCode() { return "SemiUR"; }
    @Override public int arity() { return 2; }
    @Override protected String layoutInterface() { return "SemiEnclosureUpperRightT"; }
    @Override protected String[] slotAccessorNames() { return new String[]{"wrapper", "content"}; }
}
