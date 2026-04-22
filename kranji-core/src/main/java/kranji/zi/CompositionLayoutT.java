package kranji.zi;

/**
 * Typed counterpart of {@link CompositionLayout} — one sealed root with
 * generic per-slot type parameters on each variant.
 *
 * <p>The legacy {@link CompositionLayout} carries raw
 * {@link BlockStructure} slots; a composition of 明 = 日 + 月 says only
 * "there is a left block and a right block". That erases the identity of
 * the specific components, so the compiler cannot catch structural
 * mistakes like {@code new LeftRight(Yue.INSTANCE, Ri.INSTANCE)} where
 * the author flipped the slots.</p>
 *
 * <p>This typed hierarchy parameterises each slot. For a typed variant
 * such as {@link LeftRightT}{@code <Ri, Yue>}, the types themselves state
 * that the left slot is 日 and the right slot is 月.</p>
 *
 * <p>Every typed variant provides a default {@link #legacyLayout()} that
 * projects down to the untyped {@link CompositionLayout} so that bridge
 * code, JSON serialisation, depth walkers, and all existing consumers
 * keep working unchanged.</p>
 */
public sealed interface CompositionLayoutT
        permits LeftRightT, TopBottomT, LeftMiddleRightT, TopMiddleBottomT,
                FullEnclosureT, SemiEnclosureUpperLeftT, SemiEnclosureUpperRightT,
                SemiEnclosureBottomLeftT, SemiEnclosureTopThreeT,
                SemiEnclosureBottomThreeT, SemiEnclosureLeftThreeT {

    /** Project to the untyped layout form (bridge compat). */
    CompositionLayout legacyLayout();
}
