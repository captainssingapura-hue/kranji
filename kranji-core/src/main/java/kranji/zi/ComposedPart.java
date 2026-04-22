package kranji.zi;

/**
 * An anonymous composed structural block — a pure value object that
 * wraps a {@link CompositionLayout} so it can appear as a
 * {@link BlockStructure} slot value.
 *
 * <p>{@code ComposedPart} has no identity: no name, no pinyin, no
 * etymology. Two instances with equal {@link #composition()} are equal.
 * Instantiate inline wherever a raw layout record would otherwise appear
 * as a slot value.</p>
 *
 * <p>For named composites (actual characters), use {@link ComposedZi}
 * instead.</p>
 */
public record ComposedPart(CompositionLayout composition) implements ComposedBlock {
}
