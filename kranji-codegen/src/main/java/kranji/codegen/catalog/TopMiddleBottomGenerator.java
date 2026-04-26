package kranji.codegen.catalog;

/** {@code TopMiddleBottomT<T, M, B>} — slots {@code top()}, {@code middle()}, {@code bottom()}. */
public final class TopMiddleBottomGenerator extends BaseStructureGenerator {
    @Override public String layoutCode() { return "TB3"; }
    @Override public int arity() { return 3; }
    @Override protected String layoutInterface() { return "TopMiddleBottomT"; }
    @Override protected String[] slotAccessorNames() {
        return new String[]{"top", "middle", "bottom"};
    }
}
