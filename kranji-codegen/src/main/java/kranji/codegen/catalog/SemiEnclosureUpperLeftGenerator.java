package kranji.codegen.catalog;

/** {@code SemiEnclosureUpperLeftT<W, C>} — slots {@code wrapper()}, {@code content()}. Used for 疒, 厂, 户, 广 etc. */
public final class SemiEnclosureUpperLeftGenerator extends BaseStructureGenerator {
    @Override public String layoutCode() { return "SemiUL"; }
    @Override public int arity() { return 2; }
    @Override protected String layoutInterface() { return "SemiEnclosureUpperLeftT"; }
    @Override protected String[] slotAccessorNames() { return new String[]{"wrapper", "content"}; }
}
