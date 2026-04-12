package kranji.zi;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import kranji.classification.BlockRole;
import kranji.classification.CompositionStyle;

/**
 * 合体字 — A composed character built from spatial arrangement of sub-components.
 *
 * <p>Sealed sum type with 12 composition variants. Each variant names its
 * component slots explicitly and defines {@link BlockRole} records for the
 * structural positions available in that layout.</p>
 *
 * <p>Every slot is typed as {@link Zi} — either a {@link SingularZi} leaf
 * or a nested {@code ComposedZi} — enabling recursive decomposition to
 * arbitrary depth.</p>
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "structure")
@JsonSubTypes({
        @JsonSubTypes.Type(value = ComposedZi.LeftRight.class, name = "left_right"),
        @JsonSubTypes.Type(value = ComposedZi.TopBottom.class, name = "top_bottom"),
        @JsonSubTypes.Type(value = ComposedZi.LeftMiddleRight.class, name = "left_middle_right"),
        @JsonSubTypes.Type(value = ComposedZi.TopMiddleBottom.class, name = "top_middle_bottom"),
        @JsonSubTypes.Type(value = ComposedZi.FullEnclosure.class, name = "full_enclosure"),
        @JsonSubTypes.Type(value = ComposedZi.SemiEnclosureUpperLeft.class, name = "semi_enclosure_upper_left"),
        @JsonSubTypes.Type(value = ComposedZi.SemiEnclosureUpperRight.class, name = "semi_enclosure_upper_right"),
        @JsonSubTypes.Type(value = ComposedZi.SemiEnclosureBottomLeft.class, name = "semi_enclosure_bottom_left"),
        @JsonSubTypes.Type(value = ComposedZi.SemiEnclosureTopThree.class, name = "semi_enclosure_top_three"),
        @JsonSubTypes.Type(value = ComposedZi.SemiEnclosureBottomThree.class, name = "semi_enclosure_bottom_three"),
        @JsonSubTypes.Type(value = ComposedZi.SemiEnclosureLeftThree.class, name = "semi_enclosure_left_three"),
        @JsonSubTypes.Type(value = ComposedZi.Mosaic.class, name = "mosaic")
})
public sealed interface ComposedZi extends Zi {

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
            case LeftRight lr          -> new CompositionStyle.LeftRight();
            case TopBottom tb          -> new CompositionStyle.TopBottom();
            case LeftMiddleRight lmr   -> new CompositionStyle.LeftMiddleRight();
            case TopMiddleBottom tmb   -> new CompositionStyle.TopMiddleBottom();
            case FullEnclosure fe      -> new CompositionStyle.FullEnclosure();
            case SemiEnclosureUpperLeft ul   -> new CompositionStyle.SemiEnclosureUpperLeft();
            case SemiEnclosureUpperRight ur  -> new CompositionStyle.SemiEnclosureUpperRight();
            case SemiEnclosureBottomLeft bl  -> new CompositionStyle.SemiEnclosureBottomLeft();
            case SemiEnclosureTopThree t3    -> new CompositionStyle.SemiEnclosureTopThree();
            case SemiEnclosureBottomThree b3 -> new CompositionStyle.SemiEnclosureBottomThree();
            case SemiEnclosureLeftThree l3   -> new CompositionStyle.SemiEnclosureLeftThree();
            case Mosaic m              -> new CompositionStyle.Mosaic();
        };
    }

    // ── Variants ──────────────────────────────────────────────────────

    /** 左右结构 — Two nodes side-by-side (e.g. 明 = 日 + 月, 清 = 氵 + 青). */
    record LeftRight(Zi left, Zi right) implements ComposedZi {
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
    record TopBottom(Zi top, Zi bottom) implements ComposedZi {
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
    record LeftMiddleRight(Zi left, Zi middle, Zi right) implements ComposedZi {
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
    record TopMiddleBottom(Zi top, Zi middle, Zi bottom) implements ComposedZi {
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
    record FullEnclosure(Zi outer, Zi inner) implements ComposedZi {
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
    record SemiEnclosureUpperLeft(Zi wrapper, Zi content) implements ComposedZi {
        public static final Wrapper WRAPPER = new Wrapper();
        public static final Content CONTENT = new Content();

        @Override public String glyph() { return wrapper.glyph() + content.glyph(); }
    }

    /** 右上包围 — Wrapper on upper right (e.g. 句 = 勹 + 口). */
    record SemiEnclosureUpperRight(Zi wrapper, Zi content) implements ComposedZi {
        public static final Wrapper WRAPPER = new Wrapper();
        public static final Content CONTENT = new Content();

        @Override public String glyph() { return wrapper.glyph() + content.glyph(); }
    }

    /** 左下包围 — Wrapper on lower left (e.g. 建 = 廴 + 聿, 远 = 辶 + 元). */
    record SemiEnclosureBottomLeft(Zi wrapper, Zi content) implements ComposedZi {
        public static final Wrapper WRAPPER = new Wrapper();
        public static final Content CONTENT = new Content();

        @Override public String glyph() { return wrapper.glyph() + content.glyph(); }
    }

    /** 上三包围 — Three-side top enclosure (e.g. 同 = 冂 + 口, 问 = 门 + 口). */
    record SemiEnclosureTopThree(Zi wrapper, Zi content) implements ComposedZi {
        public static final Wrapper WRAPPER = new Wrapper();
        public static final Content CONTENT = new Content();

        @Override public String glyph() { return wrapper.glyph() + content.glyph(); }
    }

    /** 下三包围 — Three-side bottom enclosure (e.g. 凶 = 凵 + 㐅). */
    record SemiEnclosureBottomThree(Zi wrapper, Zi content) implements ComposedZi {
        public static final Wrapper WRAPPER = new Wrapper();
        public static final Content CONTENT = new Content();

        @Override public String glyph() { return wrapper.glyph() + content.glyph(); }
    }

    /** 左三包围 — Three-side left enclosure (e.g. 匹 = 匚 + 儿, 区 = 匚 + 㐅). */
    record SemiEnclosureLeftThree(Zi wrapper, Zi content) implements ComposedZi {
        public static final Wrapper WRAPPER = new Wrapper();
        public static final Content CONTENT = new Content();

        @Override public String glyph() { return wrapper.glyph() + content.glyph(); }
    }

    /** 品字结构 — Three identical nodes in triangular arrangement (e.g. 品 = 口×3, 森 = 木×3). */
    record Mosaic(Zi element) implements ComposedZi {
        public record Top() implements BlockRole {
            @Override public String label() { return "top"; }
        }
        public record BottomLeft() implements BlockRole {
            @Override public String label() { return "bottom-left"; }
        }
        public record BottomRight() implements BlockRole {
            @Override public String label() { return "bottom-right"; }
        }
        public static final Top TOP = new Top();
        public static final BottomLeft BOTTOM_LEFT = new BottomLeft();
        public static final BottomRight BOTTOM_RIGHT = new BottomRight();

        @Override public String glyph() { return element.glyph() + element.glyph() + element.glyph(); }
    }
}
