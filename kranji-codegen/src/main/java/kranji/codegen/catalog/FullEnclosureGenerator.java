package kranji.codegen.catalog;

/** {@code FullEnclosureT<O, I>} — slots {@code outer()}, {@code inner()}. Used for 囗, 门. */
public final class FullEnclosureGenerator extends BaseStructureGenerator {
    @Override public String layoutCode() { return "FullEnc"; }
    @Override public int arity() { return 2; }
    @Override protected String layoutInterface() { return "FullEnclosureT"; }
    @Override protected String[] slotAccessorNames() { return new String[]{"outer", "inner"}; }
}
