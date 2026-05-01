package kranji.codegen.catalog;

/** {@code SemiEnclosureBottomThreeT<W, C>} — three-side bottom enclosure (e.g. 凵 below content). */
public final class SemiEnclosureBottomThreeGenerator extends BaseStructureGenerator {
    @Override public String layoutCode() { return "SemiB"; }
    @Override public int arity() { return 2; }
    @Override protected String layoutInterface() { return "SemiEnclosureBottomThreeT"; }
    @Override protected String[] slotAccessorNames() { return new String[]{"wrapper", "content"}; }
}
