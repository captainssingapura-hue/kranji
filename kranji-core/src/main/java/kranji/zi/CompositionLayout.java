package kranji.zi;

import java.util.List;

import kranji.classification.BlockRole;
import kranji.classification.CompositionStyle;

/**
 * The 11-variant sealed grammar of how sub-blocks are spatially arranged.
 *
 * <p>Moved out of {@link ComposedBlock} so that slot types (which remain
 * typed as {@link BlockStructure}) no longer admit raw layout records.
 * A raw {@code CompositionLayout} is <em>not</em> a {@link BlockStructure}
 * — to appear in a slot it must be wrapped in a {@link ComposedPart} or
 * referenced as a {@link ComposedZi}.</p>
 *
 * <p>Every slot inside a layout record is itself a {@link BlockStructure}
 * (atomic {@link SingularBlock} or composed {@link ComposedBlock}),
 * enabling recursive decomposition to arbitrary depth while keeping the
 * "slots are always named entities" invariant.</p>
 */
public sealed interface CompositionLayout {

    // ── Shared role types for semi-enclosures ─────────────────────────

    /** Overlay role for enclosure wrappers (shared by all semi-enclosure variants). */
    record Wrapper() implements BlockRole {
        @Override public String label() { return "wrapper"; }
        @Override public boolean isOverlay() { return true; }
    }

    /** Content role inside an enclosure (shared by all semi-enclosure variants). */
    record Content() implements BlockRole {
        @Override public String label() { return "content"; }
    }

    // ── Composition style tag ─────────────────────────────────────────

    /** Extract the pure composition style tag (no structural data). */
    default CompositionStyle style() {
        return switch (this) {
            case LeftRight lr                  -> new CompositionStyle.LeftRight();
            case TopBottom tb                  -> new CompositionStyle.TopBottom();
            case LeftMiddleRight lmr           -> new CompositionStyle.LeftMiddleRight();
            case TopMiddleBottom tmb           -> new CompositionStyle.TopMiddleBottom();
            case FullEnclosure fe              -> new CompositionStyle.FullEnclosure();
            case SemiEnclosureUpperLeft ul     -> new CompositionStyle.SemiEnclosureUpperLeft();
            case SemiEnclosureUpperRight ur    -> new CompositionStyle.SemiEnclosureUpperRight();
            case SemiEnclosureBottomLeft bl    -> new CompositionStyle.SemiEnclosureBottomLeft();
            case SemiEnclosureTopThree t3      -> new CompositionStyle.SemiEnclosureTopThree();
            case SemiEnclosureBottomThree b3   -> new CompositionStyle.SemiEnclosureBottomThree();
            case SemiEnclosureLeftThree l3     -> new CompositionStyle.SemiEnclosureLeftThree();
        };
    }

    /** Concatenated glyph view (for debugging / display). */
    String glyph();

    /**
     * The direct (level-1) components of this layout, in reading order.
     *
     * <p>Every slot in a layout record is a {@link BlockStructure} —
     * either an atomic {@link SingularBlock} or a wrapped
     * {@link ComposedBlock}. This exposes those slot values as a flat
     * list so that callers (e.g. depth detectors, glyph walkers) can
     * recurse without duplicating the variant switch.</p>
     */
    default List<BlockStructure> components() {
        return switch (this) {
            case LeftRight lr                -> List.of(lr.left(), lr.right());
            case TopBottom tb                -> List.of(tb.top(), tb.bottom());
            case LeftMiddleRight lmr         -> List.of(lmr.left(), lmr.middle(), lmr.right());
            case TopMiddleBottom tmb         -> List.of(tmb.top(), tmb.middle(), tmb.bottom());
            case FullEnclosure fe            -> List.of(fe.outer(), fe.inner());
            case SemiEnclosureUpperLeft ul   -> List.of(ul.wrapper(), ul.content());
            case SemiEnclosureUpperRight ur  -> List.of(ur.wrapper(), ur.content());
            case SemiEnclosureBottomLeft bl  -> List.of(bl.wrapper(), bl.content());
            case SemiEnclosureTopThree t3    -> List.of(t3.wrapper(), t3.content());
            case SemiEnclosureBottomThree b3 -> List.of(b3.wrapper(), b3.content());
            case SemiEnclosureLeftThree l3   -> List.of(l3.wrapper(), l3.content());
        };
    }

    // ── Variants ──────────────────────────────────────────────────────

    /** 左右结构 — Two nodes side-by-side (e.g. 明 = 日 + 月, 清 = 氵 + 青). */
    record LeftRight(BlockStructure left, BlockStructure right) implements CompositionLayout {
        public record Left() implements BlockRole {
            @Override public String label() { return "left"; }
        }
        public record Right() implements BlockRole {
            @Override public String label() { return "right"; }
        }
        public static final Left LEFT = new Left();
        public static final Right RIGHT = new Right();

        @Override public String glyph() { return left.glyph() + right.glyph(); }
    }

    /** 上下结构 — Two nodes stacked vertically (e.g. 字 = 宀 + 子, 花 = 艹 + 化). */
    record TopBottom(BlockStructure top, BlockStructure bottom) implements CompositionLayout {
        public record Top() implements BlockRole {
            @Override public String label() { return "top"; }
        }
        public record Bottom() implements BlockRole {
            @Override public String label() { return "bottom"; }
        }
        public static final Top TOP = new Top();
        public static final Bottom BOTTOM = new Bottom();

        @Override public String glyph() { return top.glyph() + bottom.glyph(); }
    }

    /** 左中右结构 — Three nodes horizontally (e.g. 做 = 亻 + 古 + 攵). */
    record LeftMiddleRight(BlockStructure left, BlockStructure middle, BlockStructure right) implements CompositionLayout {
        public record Left() implements BlockRole {
            @Override public String label() { return "left"; }
        }
        public record Middle() implements BlockRole {
            @Override public String label() { return "middle"; }
        }
        public record Right() implements BlockRole {
            @Override public String label() { return "right"; }
        }
        public static final Left LEFT = new Left();
        public static final Middle MIDDLE = new Middle();
        public static final Right RIGHT = new Right();

        @Override public String glyph() { return left.glyph() + middle.glyph() + right.glyph(); }
    }

    /** 上中下结构 — Three nodes vertically (e.g. 意 = 立 + 日 + 心). */
    record TopMiddleBottom(BlockStructure top, BlockStructure middle, BlockStructure bottom) implements CompositionLayout {
        public record Top() implements BlockRole {
            @Override public String label() { return "top"; }
        }
        public record Middle() implements BlockRole {
            @Override public String label() { return "middle"; }
        }
        public record Bottom() implements BlockRole {
            @Override public String label() { return "bottom"; }
        }
        public static final Top TOP = new Top();
        public static final Middle MIDDLE = new Middle();
        public static final Bottom BOTTOM = new Bottom();

        @Override public String glyph() { return top.glyph() + middle.glyph() + bottom.glyph(); }
    }

    /** 全包围 — Outer node fully surrounds inner (e.g. 国 = 囗 + 玉). */
    record FullEnclosure(BlockStructure outer, BlockStructure inner) implements CompositionLayout {
        public record OuterFrame() implements BlockRole {
            @Override public String label() { return "frame"; }
            @Override public boolean isOverlay() { return true; }
        }
        public record Inner() implements BlockRole {
            @Override public String label() { return "inner"; }
        }
        public static final OuterFrame OUTER = new OuterFrame();
        public static final Inner INNER = new Inner();

        @Override public String glyph() { return outer.glyph() + inner.glyph(); }
    }

    /** 左上包围 — Wrapper on upper left (e.g. 厅 = 厂 + 丁, 病 = 疒 + 丙). */
    record SemiEnclosureUpperLeft(BlockStructure wrapper, BlockStructure content) implements CompositionLayout {
        public static final Wrapper WRAPPER = new Wrapper();
        public static final Content CONTENT = new Content();

        @Override public String glyph() { return wrapper.glyph() + content.glyph(); }
    }

    /** 右上包围 — Wrapper on upper right (e.g. 句 = 勹 + 口). */
    record SemiEnclosureUpperRight(BlockStructure wrapper, BlockStructure content) implements CompositionLayout {
        public static final Wrapper WRAPPER = new Wrapper();
        public static final Content CONTENT = new Content();

        @Override public String glyph() { return wrapper.glyph() + content.glyph(); }
    }

    /** 左下包围 — Wrapper on lower left (e.g. 建 = 廴 + 聿, 远 = 辶 + 元). */
    record SemiEnclosureBottomLeft(BlockStructure wrapper, BlockStructure content) implements CompositionLayout {
        public static final Wrapper WRAPPER = new Wrapper();
        public static final Content CONTENT = new Content();

        @Override public String glyph() { return wrapper.glyph() + content.glyph(); }
    }

    /** 上三包围 — Three-side top enclosure (e.g. 同 = 冂 + 口, 问 = 门 + 口). */
    record SemiEnclosureTopThree(BlockStructure wrapper, BlockStructure content) implements CompositionLayout {
        public static final Wrapper WRAPPER = new Wrapper();
        public static final Content CONTENT = new Content();

        @Override public String glyph() { return wrapper.glyph() + content.glyph(); }
    }

    /** 下三包围 — Three-side bottom enclosure (e.g. 凶 = 凵 + 㐅). */
    record SemiEnclosureBottomThree(BlockStructure wrapper, BlockStructure content) implements CompositionLayout {
        public static final Wrapper WRAPPER = new Wrapper();
        public static final Content CONTENT = new Content();

        @Override public String glyph() { return wrapper.glyph() + content.glyph(); }
    }

    /** 左三包围 — Three-side left enclosure (e.g. 匹 = 匚 + 儿, 区 = 匚 + 㐅). */
    record SemiEnclosureLeftThree(BlockStructure wrapper, BlockStructure content) implements CompositionLayout {
        public static final Wrapper WRAPPER = new Wrapper();
        public static final Content CONTENT = new Content();

        @Override public String glyph() { return wrapper.glyph() + content.glyph(); }
    }
}
