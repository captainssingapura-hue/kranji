package kranji.characters;

import kranji.pinyin.Tone;
import kranji.zi.ComposedZi.*;
import kranji.zi.SingularZi;
import kranji.zi.Zi;

/**
 * Static factory methods for building composition trees concisely.
 *
 * <p>Designed for static import: {@code import static kranji.characters.Comp.*;}
 * so that character definitions read as compact, indented trees.</p>
 *
 * <p>Short names ({@code lr}, {@code tb}, {@code tmb}, etc.) are used for
 * nested inner nodes. The outer-level composition is typically set via the
 * readable names on {@link EntryBuilder} ({@code .leftRight()}, {@code .topBottom()}, etc.).</p>
 */
public final class Comp {

    private Comp() {}

    // ── Tone aliases ───────────────────────────────────────────────────

    public static final Tone T1 = Tone.FIRST;
    public static final Tone T2 = Tone.SECOND;
    public static final Tone T3 = Tone.THIRD;
    public static final Tone T4 = Tone.FOURTH;
    public static final Tone T0 = Tone.NEUTRAL;
    /** Alias: T5 = neutral tone (same as T0, matches tone number 5). */
    public static final Tone T5 = Tone.NEUTRAL;

    // ── Leaf node ──────────────────────────────────────────────────────

    /**
     * Creates a plain standalone-character leaf node.
     * Prefer direct static references (e.g. {@code HintedZi.RI}) for
     * characters that have dedicated records with layout hints.
     */
    public static SingularZi zi(String glyph) {
        return SingularZi.plain(glyph);
    }

    // ── 2-slot compositions ────────────────────────────────────────────

    /** 左右结构 — Left-Right. */
    public static LeftRight lr(Zi left, Zi right) {
        return new LeftRight(left, right);
    }

    /** 上下结构 — Top-Bottom. */
    public static TopBottom tb(Zi top, Zi bottom) {
        return new TopBottom(top, bottom);
    }

    /** 全包围 — Full Enclosure. */
    public static FullEnclosure encl(Zi outer, Zi inner) {
        return new FullEnclosure(outer, inner);
    }

    // ── 3-slot compositions ────────────────────────────────────────────

    /** 左中右结构 — Left-Middle-Right. */
    public static LeftMiddleRight lmr(Zi left, Zi middle, Zi right) {
        return new LeftMiddleRight(left, middle, right);
    }

    /** 上中下结构 — Top-Middle-Bottom. */
    public static TopMiddleBottom tmb(Zi top, Zi middle, Zi bottom) {
        return new TopMiddleBottom(top, middle, bottom);
    }

    // ── Semi-enclosures (wrapper + content) ────────────────────────────

    /** 左上包围 — Semi-enclosure upper-left. */
    public static SemiEnclosureUpperLeft semiUL(Zi wrapper, Zi content) {
        return new SemiEnclosureUpperLeft(wrapper, content);
    }

    /** 右上包围 — Semi-enclosure upper-right. */
    public static SemiEnclosureUpperRight semiUR(Zi wrapper, Zi content) {
        return new SemiEnclosureUpperRight(wrapper, content);
    }

    /** 左下包围 — Semi-enclosure bottom-left. */
    public static SemiEnclosureBottomLeft semiBL(Zi wrapper, Zi content) {
        return new SemiEnclosureBottomLeft(wrapper, content);
    }

    /** 上三包围 — Semi-enclosure top-three. */
    public static SemiEnclosureTopThree semiT3(Zi wrapper, Zi content) {
        return new SemiEnclosureTopThree(wrapper, content);
    }

    /** 下三包围 — Semi-enclosure bottom-three. */
    public static SemiEnclosureBottomThree semiB3(Zi wrapper, Zi content) {
        return new SemiEnclosureBottomThree(wrapper, content);
    }

    /** 左三包围 — Semi-enclosure left-three. */
    public static SemiEnclosureLeftThree semiL3(Zi wrapper, Zi content) {
        return new SemiEnclosureLeftThree(wrapper, content);
    }

    // ── Mosaic ─────────────────────────────────────────────────────────

    /** 品字结构 — Mosaic (three identical elements). */
    public static Mosaic mosaic(Zi element) {
        return new Mosaic(element);
    }
}
