package kranji.codegen.catalog;

/** {@code SemiEnclosureLeftThreeT<W, C>} — three-side left enclosure (e.g. 匚 to the right of content). */
public final class SemiEnclosureLeftThreeGenerator extends BaseStructureGenerator {
    @Override public String layoutCode() { return "SemiL"; }
    @Override public int arity() { return 2; }
    @Override protected String layoutInterface() { return "SemiEnclosureLeftThreeT"; }
    @Override protected String[] slotAccessorNames() { return new String[]{"wrapper", "content"}; }
}
