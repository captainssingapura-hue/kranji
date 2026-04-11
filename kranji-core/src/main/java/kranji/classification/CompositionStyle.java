package kranji.classification;

/**
 * 字形结构类型 — The spatial arrangement pattern of a Chinese character,
 * modeled as a pure classification tag without structural data.
 *
 * <p>Each variant is a stateless record that identifies the composition
 * pattern. This is orthogonal to {@link CharacterComposition} which carries
 * the actual child nodes — {@code CompositionStyle} is the pattern identity
 * alone, useful for classification, filtering, and display.</p>
 *
 * <p>Not sealed — open for extension if new composition patterns are
 * identified.</p>
 */
public interface CompositionStyle {

    /** The Chinese name of this composition pattern. */
    String chineseName();

    /** 独体字 — Single indivisible unit (e.g. 人, 山). */
    record Singular() implements CompositionStyle {
        @Override public String chineseName() { return "独体字"; }
    }

    /** 左右结构 — Two components side-by-side (e.g. 明 = 日+月). */
    record LeftRight() implements CompositionStyle {
        @Override public String chineseName() { return "左右结构"; }
    }

    /** 上下结构 — Two components stacked vertically (e.g. 字 = 宀+子). */
    record TopBottom() implements CompositionStyle {
        @Override public String chineseName() { return "上下结构"; }
    }

    /** 左中右结构 — Three components horizontally (e.g. 做 = 亻+古+攵). */
    record LeftMiddleRight() implements CompositionStyle {
        @Override public String chineseName() { return "左中右结构"; }
    }

    /** 上中下结构 — Three components vertically (e.g. 意 = 立+日+心). */
    record TopMiddleBottom() implements CompositionStyle {
        @Override public String chineseName() { return "上中下结构"; }
    }

    /** 全包围 — Outer fully surrounds inner (e.g. 国 = 囗+玉). */
    record FullEnclosure() implements CompositionStyle {
        @Override public String chineseName() { return "全包围"; }
    }

    /** 左上包围 — Wrapper on upper left (e.g. 病 = 疒+丙). */
    record SemiEnclosureUpperLeft() implements CompositionStyle {
        @Override public String chineseName() { return "左上包围"; }
    }

    /** 右上包围 — Wrapper on upper right (e.g. 句 = 勹+口). */
    record SemiEnclosureUpperRight() implements CompositionStyle {
        @Override public String chineseName() { return "右上包围"; }
    }

    /** 左下包围 — Wrapper on lower left (e.g. 远 = 辶+元). */
    record SemiEnclosureBottomLeft() implements CompositionStyle {
        @Override public String chineseName() { return "左下包围"; }
    }

    /** 上三包围 — Three-side top enclosure (e.g. 问 = 门+口). */
    record SemiEnclosureTopThree() implements CompositionStyle {
        @Override public String chineseName() { return "上三包围"; }
    }

    /** 下三包围 — Three-side bottom enclosure (e.g. 凶 = 凵+㐅). */
    record SemiEnclosureBottomThree() implements CompositionStyle {
        @Override public String chineseName() { return "下三包围"; }
    }

    /** 左三包围 — Three-side left enclosure (e.g. 区 = 匚+㐅). */
    record SemiEnclosureLeftThree() implements CompositionStyle {
        @Override public String chineseName() { return "左三包围"; }
    }

    /** 品字结构 — Triangular arrangement of three identical elements (e.g. 品 = 口×3). */
    record Mosaic() implements CompositionStyle {
        @Override public String chineseName() { return "品字结构"; }
    }
}
