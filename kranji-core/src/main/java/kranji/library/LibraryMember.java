package kranji.library;

import kranji.zi.SingularZi;

/**
 * A character component bound to a specific {@link ZiLibrary}.
 *
 * <p>The generic parameter {@code L} ties each component to exactly one
 * library at compile time. This enables validation that compositions
 * only reference components from the same library or its dependencies.</p>
 *
 * <p>Extends {@link SingularZi} — both standalone characters (木, 日) and
 * parts/偏旁 (氵, 亻) implement this interface when they belong to a library.</p>
 *
 * @param <L> the library this component belongs to
 */
public interface LibraryMember<L extends ZiLibrary> extends SingularZi {

    /** The library instance this component belongs to. */
    L library();
}
