package kranji.simple;

import kranji.pinyin.Final;
import kranji.pinyin.Initial;
import kranji.pinyin.PinyinSyllable;
import kranji.pinyin.Tone;
import kranji.zi.ZiCharUTF8;
import kranji.zi.ZiCharUTF8Codec;

import java.util.List;
import java.util.Objects;

/**
 * The authoring surface for the simple tier.
 *
 * <p>Entries are Java rather than a data file so the compiler checks them:
 * {@code ANG} and {@code SECOND} are constants, and a mistyped final is a build
 * error on the offending line instead of a parse failure at startup or, worse,
 * a wrong reading shown to a child.</p>
 *
 * <p>A partition names its initial once and the entries inherit it, so each
 * line carries only what varies:</p>
 *
 * <pre>{@code
 * import static kranji.simple.Finals.*;
 * import static kranji.pinyin.Tone.*;
 *
 * public final class H {
 *     private static final SimpleZiDsl D = SimpleZiDsl.forInitial(Initial.H);
 *
 *     public static final List<SimpleZi> ENTRIES = List.of(
 *             D.zi("行", ANG, SECOND, "to go; a row").also(Initial.X, ING, SECOND),
 *             D.zi("航", ANG, SECOND, "navigate"),
 *             D.zi("好", AO,  THIRD,  "good").also(H, AO, FOURTH)
 *     );
 * }
 * }</pre>
 *
 * <p>Because the partition supplies the initial, a character can only be
 * written into the file matching its default reading — and a test asserts it.
 * The layout is not a filing convention; it is a checked invariant.</p>
 */
public final class SimpleZiDsl {

    private final Initial initial;

    private SimpleZiDsl(Initial initial) {
        this.initial = Objects.requireNonNull(initial, "initial");
    }

    /** A DSL bound to one partition's initial. */
    public static SimpleZiDsl forInitial(Initial initial) {
        return new SimpleZiDsl(initial);
    }

    /** The initial every entry from this DSL carries as its default. */
    public Initial initial() {
        return initial;
    }

    /**
     * One character, read with this partition's initial.
     *
     * <p>Takes the glyph as a {@link String} so entries read as the character
     * itself; it is converted to {@link ZiCharUTF8} here, so a non-Han or
     * multi-codepoint entry fails at class-initialisation rather than being
     * carried silently.</p>
     */
    public SimpleZi zi(String glyph, Final fin, Tone tone) {
        return new SimpleZi(ZiCharUTF8Codec.INSTANCE.from(glyph),
                new PinyinSyllable(initial, fin, tone), List.of());
    }

    /**
     * One character whose default reading is a syllabic consonant — {@code zhi
     * chi shi ri zi ci si}. The nucleus is empty; the placeholder {@code i} is
     * supplied on display.
     */
    public SimpleZi syllabic(String glyph, Tone tone) {
        return zi(glyph, Finals.SYLLABIC, tone);
    }

    /**
     * A syllable and the characters read as it, all of them principally.
     *
     * <p>The common case: the final and tone are stated once for the group
     * rather than once per character, and every character listed has this as
     * its main reading. Use {@link #syl(Final, Tone, ZiDecl...)} for a
     * syllable that also carries a character emphasised elsewhere.</p>
     */
    public SyllableDecl syl(Final fin, Tone tone, String... glyphs) {
        Objects.requireNonNull(glyphs, "glyphs");
        var decls = new java.util.ArrayList<ZiDecl>(glyphs.length);
        for (String glyph : glyphs) decls.add(ZiDecl.of(glyph));
        return new SyllableDecl(new PinyinSyllable(initial, fin, tone), decls);
    }

    /**
     * A syllable whose characters are not all principally read this way.
     *
     * <p>{@code ZiDecl.of} emphasises, {@code ZiDecl.alt} does not. A
     * polyphonic character is emphasised exactly once across the whole
     * corpus and appears as an alternate everywhere else it is read.</p>
     */
    public SyllableDecl syl(Final fin, Tone tone, ZiDecl... decls) {
        Objects.requireNonNull(decls, "decls");
        return new SyllableDecl(new PinyinSyllable(initial, fin, tone), List.of(decls));
    }
}
