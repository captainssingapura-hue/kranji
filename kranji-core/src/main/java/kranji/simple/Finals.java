package kranji.simple;

import kranji.pinyin.Body;
import kranji.pinyin.Final;
import kranji.pinyin.Head;
import kranji.pinyin.Tail;

/**
 * Named constants for the 36 Mandarin finals.
 *
 * <p>{@link Final} is a record over {@code (Head, Body, Tail)} rather than an
 * enum, so there is no {@code Final.ANG} to name in a data file. Writing those
 * triples inline at every entry would be unreadable and easy to get subtly
 * wrong — and a wrong final produces a wrong reading for every character that
 * uses it.</p>
 *
 * <p>Every triple below was derived from {@link kranji.pinyin.PinyinSyllable#parse}
 * rather than reasoned out, and {@code FinalsTest} asserts each one still
 * round-trips to its expected spelling. Several are not what you would guess:
 * {@code in} and {@code ing} are open-headed with an {@code I} body, while
 * {@code ian} and {@code iang} carry an {@code I} head; {@code iu} is really
 * {@code iou} and {@code ui} is really {@code uei}.</p>
 */
public final class Finals {

    private Finals() {}

    private static Final f(Head head, Body body, Tail tail) {
        return new Final(head, body, tail);
    }

    // ── Open finals ────────────────────────────────────────────────────
    /** {@code a} */   public static final Final A   = f(Head.OPEN, Body.A,  Tail.NONE);
    /** {@code o} */   public static final Final O   = f(Head.OPEN, Body.O,  Tail.NONE);
    /** {@code e} */   public static final Final E   = f(Head.OPEN, Body.E,  Tail.NONE);
    /** {@code er} */  public static final Final ER  = f(Head.OPEN, Body.ER, Tail.NONE);
    /** {@code ai} */  public static final Final AI  = f(Head.OPEN, Body.A,  Tail.VOWEL_I);
    /** {@code ei} */  public static final Final EI  = f(Head.OPEN, Body.E,  Tail.VOWEL_I);
    /** {@code ao} */  public static final Final AO  = f(Head.OPEN, Body.A,  Tail.VOWEL_U);
    /** {@code ou} */  public static final Final OU  = f(Head.OPEN, Body.O,  Tail.VOWEL_U);
    /** {@code an} */  public static final Final AN  = f(Head.OPEN, Body.A,  Tail.N);
    /** {@code en} */  public static final Final EN  = f(Head.OPEN, Body.E,  Tail.N);
    /** {@code ang} */ public static final Final ANG = f(Head.OPEN, Body.A,  Tail.NG);
    /** {@code eng} */ public static final Final ENG = f(Head.OPEN, Body.E,  Tail.NG);
    /** {@code ong} */ public static final Final ONG = f(Head.OPEN, Body.O,  Tail.NG);

    // ── i- finals ──────────────────────────────────────────────────────
    /** {@code i} */    public static final Final I    = f(Head.OPEN, Body.I,       Tail.NONE);
    /** {@code ia} */   public static final Final IA   = f(Head.I,    Body.A,       Tail.NONE);
    /** {@code ie} */   public static final Final IE   = f(Head.I,    Body.E_CARON, Tail.NONE);
    /** {@code iao} */  public static final Final IAO  = f(Head.I,    Body.A,       Tail.VOWEL_U);
    /** {@code iou}, written {@code iu} after an initial. */
    public static final Final IOU  = f(Head.I,    Body.O,       Tail.VOWEL_U);
    /** {@code ian} */  public static final Final IAN  = f(Head.I,    Body.A,       Tail.N);
    /** {@code in} — open head, not an i-head. */
    public static final Final IN   = f(Head.OPEN, Body.I,       Tail.N);
    /** {@code iang} */ public static final Final IANG = f(Head.I,    Body.A,       Tail.NG);
    /** {@code ing} — open head, not an i-head. */
    public static final Final ING  = f(Head.OPEN, Body.I,       Tail.NG);
    /** {@code iong} */ public static final Final IONG = f(Head.I,    Body.O,       Tail.NG);

    // ── u- finals ──────────────────────────────────────────────────────
    /** {@code u} */    public static final Final U    = f(Head.U, Body.U, Tail.NONE);
    /** {@code ua} */   public static final Final UA   = f(Head.U, Body.A, Tail.NONE);
    /** {@code uo} */   public static final Final UO   = f(Head.U, Body.O, Tail.NONE);
    /** {@code uai} */  public static final Final UAI  = f(Head.U, Body.A, Tail.VOWEL_I);
    /** {@code uei}, written {@code ui} after an initial. */
    public static final Final UEI  = f(Head.U, Body.E, Tail.VOWEL_I);
    /** {@code uan} */  public static final Final UAN  = f(Head.U, Body.A, Tail.N);
    /** {@code uen}, written {@code un} after an initial. */
    public static final Final UEN  = f(Head.U, Body.E, Tail.N);
    /** {@code uang} */ public static final Final UANG = f(Head.U, Body.A, Tail.NG);
    /** {@code ueng} */ public static final Final UENG = f(Head.U, Body.E, Tail.NG);

    // ── ü- finals ──────────────────────────────────────────────────────
    /** {@code ü} */    public static final Final V    = f(Head.V, Body.V,       Tail.NONE);
    /** {@code üe} */   public static final Final VE   = f(Head.V, Body.E_CARON, Tail.NONE);
    /** {@code üan} */  public static final Final VAN  = f(Head.V, Body.A,       Tail.N);
    /** {@code ün} */   public static final Final VN   = f(Head.V, Body.E,       Tail.N);

    /**
     * The empty final of the syllabic consonants — {@code zhi chi shi ri zi ci
     * si}. The nucleus is acoustically empty; pinyin writes a placeholder
     * {@code i} which {@link kranji.pinyin.PinyinSyllable} supplies on display.
     */
    public static final Final SYLLABIC = f(Head.OPEN, Body.NULL, Tail.NONE);
}
