package kranji.codegen.catalog;

/** {@code SemiEnclosureTopThreeT<W, C>} — three-side top enclosure (e.g. 冂 over content). */
public final class SemiEnclosureTopThreeGenerator extends BaseStructureGenerator {
    @Override public String layoutCode() { return "SemiU"; }
    @Override public int arity() { return 2; }
    @Override protected String layoutInterface() { return "SemiEnclosureTopThreeT"; }
    @Override protected String[] slotAccessorNames() { return new String[]{"wrapper", "content"}; }
}
