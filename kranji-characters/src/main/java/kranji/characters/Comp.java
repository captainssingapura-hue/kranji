package kranji.characters;

import kranji.classification.CharacterComposition.*;
import kranji.classification.StructuralNode;
import kranji.component.Component.Zi;
import kranji.layout.BlockRole;
import kranji.layout.LayoutHint;
import kranji.pinyin.Tone;

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

    /** Creates a standalone-character leaf node. */
    public static Zi zi(String glyph) {
        return new Zi(glyph);
    }

    // ── 2-slot compositions ────────────────────────────────────────────

    /** 左右结构 — Left-Right. */
    public static LeftRight lr(StructuralNode left, StructuralNode right) {
        return new LeftRight(left, right);
    }

    /** 上下结构 — Top-Bottom. */
    public static TopBottom tb(StructuralNode top, StructuralNode bottom) {
        return new TopBottom(top, bottom);
    }

    /** 全包围 — Full Enclosure. */
    public static FullEnclosure encl(StructuralNode outer, StructuralNode inner) {
        return new FullEnclosure(outer, inner);
    }

    // ── 3-slot compositions ────────────────────────────────────────────

    /** 左中右结构 — Left-Middle-Right. */
    public static LeftMiddleRight lmr(StructuralNode left, StructuralNode middle, StructuralNode right) {
        return new LeftMiddleRight(left, middle, right);
    }

    /** 上中下结构 — Top-Middle-Bottom. */
    public static TopMiddleBottom tmb(StructuralNode top, StructuralNode middle, StructuralNode bottom) {
        return new TopMiddleBottom(top, middle, bottom);
    }

    // ── Semi-enclosures (wrapper + content) ────────────────────────────

    /** 左上包围 — Semi-enclosure upper-left. */
    public static SemiEnclosureUpperLeft semiUL(StructuralNode wrapper, StructuralNode content) {
        return new SemiEnclosureUpperLeft(wrapper, content);
    }

    /** 右上包围 — Semi-enclosure upper-right. */
    public static SemiEnclosureUpperRight semiUR(StructuralNode wrapper, StructuralNode content) {
        return new SemiEnclosureUpperRight(wrapper, content);
    }

    /** 左下包围 — Semi-enclosure bottom-left. */
    public static SemiEnclosureBottomLeft semiBL(StructuralNode wrapper, StructuralNode content) {
        return new SemiEnclosureBottomLeft(wrapper, content);
    }

    /** 上三包围 — Semi-enclosure top-three. */
    public static SemiEnclosureTopThree semiT3(StructuralNode wrapper, StructuralNode content) {
        return new SemiEnclosureTopThree(wrapper, content);
    }

    /** 下三包围 — Semi-enclosure bottom-three. */
    public static SemiEnclosureBottomThree semiB3(StructuralNode wrapper, StructuralNode content) {
        return new SemiEnclosureBottomThree(wrapper, content);
    }

    /** 左三包围 — Semi-enclosure left-three. */
    public static SemiEnclosureLeftThree semiL3(StructuralNode wrapper, StructuralNode content) {
        return new SemiEnclosureLeftThree(wrapper, content);
    }

    // ── Mosaic ─────────────────────────────────────────────────────────

    /** 品字结构 — Mosaic (three identical elements). */
    public static Mosaic mosaic(StructuralNode element) {
        return new Mosaic(element);
    }

    // ── Layout hint factories ─────────────────────────────────────────

    /** Scale override (no glyph swap). */
    public static LayoutHint scale(double widthWeight, double heightWeight) {
        return LayoutHint.scale(widthWeight, heightWeight);
    }

    /** Glyph swap (no scale override). */
    public static LayoutHint glyph(String glyph) {
        return LayoutHint.glyph(glyph);
    }

    /** Glyph swap + scale override. */
    public static LayoutHint glyphAndScale(String glyph, double widthWeight, double heightWeight) {
        return LayoutHint.glyphAndScale(glyph, widthWeight, heightWeight);
    }

    // ── BlockRole aliases for common positions ────────────────────────

    public static final BlockRole LEFT   = BlockRole.LEFT;
    public static final BlockRole RIGHT  = BlockRole.RIGHT;
    public static final BlockRole TOP    = BlockRole.TOP;
    public static final BlockRole BOTTOM = BlockRole.BOTTOM;
}
