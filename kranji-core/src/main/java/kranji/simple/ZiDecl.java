package kranji.simple;

import hue.captains.singapura.tao.ontology.ValueObject;
import kranji.zi.ZiCharUTF8;
import kranji.zi.ZiCharUTF8Codec;

/**
 * One character's appearance under one syllable.
 *
 * <p>A character is declared wherever it is read, so a polyphonic character
 * simply appears more than once and needs no special handling. What each
 * appearance carries is whether <em>this</em> reading is the character's
 * principal one.</p>
 *
 * <p>Emphasis is a property of the appearance rather than of the character,
 * which is what lets sound and shape stay decoupled: the corpus declares
 * sounds, and the character's principal reading falls out of exactly one of
 * those declarations being marked. Whether that holds is checked rather than
 * assumed - see {@link PhonicDeclarations}.</p>
 */
public sealed interface ZiDecl extends ValueObject {

    /** The character being declared. */
    ZiCharUTF8 zi();

    /** True when this syllable is the character's principal reading. */
    boolean principal();

    /** The character, read here principally. */
    static ZiDecl of(String glyph) {
        return new Principal(ZiCharUTF8Codec.INSTANCE.from(glyph));
    }

    /** The character, also read here - but principally read elsewhere. */
    static ZiDecl alt(String glyph) {
        return new Alternate(ZiCharUTF8Codec.INSTANCE.from(glyph));
    }

    /** The emphasised appearance. Exactly one per character. */
    record Principal(ZiCharUTF8 zi) implements ZiDecl {
        @Override public boolean principal() { return true; }
    }

    /** A further reading of a character emphasised under some other syllable. */
    record Alternate(ZiCharUTF8 zi) implements ZiDecl {
        @Override public boolean principal() { return false; }
    }
}
