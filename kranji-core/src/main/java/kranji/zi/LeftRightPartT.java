package kranji.zi;

/**
 * Anonymous left-right composite — a value-object wrapper for
 * inline composed slots that have no name/pinyin/etymology of
 * their own.
 *
 * <p>Example use: if a depth-3 character has a mid-level
 * {@code LeftRight(氵, 青)} fragment that isn't itself a named Zi,
 * author it as {@code new LeftRightPartT<>(ShuiPang.INSTANCE, Qing.INSTANCE)}.
 * The outer record still types the slots at compile time.</p>
 *
 * <p>Stateful record (unlike the stateless {@code ComposedZiT}
 * singletons) because its value is defined entirely by the slot
 * instances passed in.</p>
 *
 * @param <L> type of the left block
 * @param <R> type of the right block
 */
public record LeftRightPartT<L extends BlockStructure, R extends BlockStructure>(L left, R right)
        implements ComposedBlock, LeftRightT<L, R> {

    @Override
    public CompositionLayout composition() {
        return legacyLayout();
    }
}
