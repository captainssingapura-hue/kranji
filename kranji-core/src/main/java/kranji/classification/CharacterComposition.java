package kranji.classification;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * 字形结构 — The structural composition of a Chinese character, modeled as a
 * proper sum type where each variant names its component slots explicitly.
 *
 * <p>Given a composition, you know exactly how many components there are and
 * what positional role each one plays — no loose lists, no separate position enum.</p>
 *
 * <p>Every positional slot is typed as {@link StructuralNode} — either a leaf
 * {@link Component} or a nested {@code CharacterComposition} — enabling recursive
 * decomposition to arbitrary depth. This captures the fractal nature of Chinese
 * characters: 𰻝 decomposes 4 levels deep, each level a spatial layout of sub-trees.</p>
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "structure")
@JsonSubTypes({
        @JsonSubTypes.Type(value = CharacterComposition.Singular.class, name = "singular"),
        @JsonSubTypes.Type(value = CharacterComposition.LeftRight.class, name = "left_right"),
        @JsonSubTypes.Type(value = CharacterComposition.TopBottom.class, name = "top_bottom"),
        @JsonSubTypes.Type(value = CharacterComposition.LeftMiddleRight.class, name = "left_middle_right"),
        @JsonSubTypes.Type(value = CharacterComposition.TopMiddleBottom.class, name = "top_middle_bottom"),
        @JsonSubTypes.Type(value = CharacterComposition.FullEnclosure.class, name = "full_enclosure"),
        @JsonSubTypes.Type(value = CharacterComposition.SemiEnclosureUpperLeft.class, name = "semi_enclosure_upper_left"),
        @JsonSubTypes.Type(value = CharacterComposition.SemiEnclosureUpperRight.class, name = "semi_enclosure_upper_right"),
        @JsonSubTypes.Type(value = CharacterComposition.SemiEnclosureBottomLeft.class, name = "semi_enclosure_bottom_left"),
        @JsonSubTypes.Type(value = CharacterComposition.SemiEnclosureTopThree.class, name = "semi_enclosure_top_three"),
        @JsonSubTypes.Type(value = CharacterComposition.SemiEnclosureBottomThree.class, name = "semi_enclosure_bottom_three"),
        @JsonSubTypes.Type(value = CharacterComposition.SemiEnclosureLeftThree.class, name = "semi_enclosure_left_three"),
        @JsonSubTypes.Type(value = CharacterComposition.Mosaic.class, name = "mosaic")
})
public sealed interface CharacterComposition extends StructuralNode {

    /** 独体字 — Single unit, no meaningful sub-parts (e.g. 人, 山, 日, 月). */
    record Singular() implements CharacterComposition {}

    /** 左右结构 — Two nodes side-by-side (e.g. 明 = 日 + 月, 清 = 氵 + 青). */
    record LeftRight(StructuralNode left, StructuralNode right) implements CharacterComposition {}

    /** 上下结构 — Two nodes stacked vertically (e.g. 字 = 宀 + 子, 花 = 艹 + 化). */
    record TopBottom(StructuralNode top, StructuralNode bottom) implements CharacterComposition {}

    /** 左中右结构 — Three nodes horizontally (e.g. 做 = 亻 + 古 + 攵, 谢 = 讠 + 身 + 寸). */
    record LeftMiddleRight(StructuralNode left, StructuralNode middle, StructuralNode right) implements CharacterComposition {}

    /** 上中下结构 — Three nodes vertically (e.g. 意 = 立 + 日 + 心). */
    record TopMiddleBottom(StructuralNode top, StructuralNode middle, StructuralNode bottom) implements CharacterComposition {}

    /** 全包围 — Outer node fully surrounds inner (e.g. 国 = 囗 + 玉, 回 = 囗 + 口). */
    record FullEnclosure(StructuralNode outer, StructuralNode inner) implements CharacterComposition {}

    /** 左上包围 — Wrapper on upper left (e.g. 厅 = 厂 + 丁, 病 = 疒 + 丙). */
    record SemiEnclosureUpperLeft(StructuralNode wrapper, StructuralNode content) implements CharacterComposition {}

    /** 右上包围 — Wrapper on upper right (e.g. 句 = 勹 + 口, 戒 = 廾 + 戈). */
    record SemiEnclosureUpperRight(StructuralNode wrapper, StructuralNode content) implements CharacterComposition {}

    /** 左下包围 — Wrapper on lower left (e.g. 建 = 廴 + 聿, 远 = 辶 + 元). */
    record SemiEnclosureBottomLeft(StructuralNode wrapper, StructuralNode content) implements CharacterComposition {}

    /** 上三包围 — Three-side top enclosure (e.g. 同 = 冂 + 口, 问 = 门 + 口). */
    record SemiEnclosureTopThree(StructuralNode wrapper, StructuralNode content) implements CharacterComposition {}

    /** 下三包围 — Three-side bottom enclosure (e.g. 凶 = 凵 + 㐅). */
    record SemiEnclosureBottomThree(StructuralNode wrapper, StructuralNode content) implements CharacterComposition {}

    /** 左三包围 — Three-side left enclosure (e.g. 匹 = 匚 + 儿, 区 = 匚 + 㐅). */
    record SemiEnclosureLeftThree(StructuralNode wrapper, StructuralNode content) implements CharacterComposition {}

    /** 品字结构 — Three identical nodes in triangular arrangement (e.g. 品 = 口×3, 森 = 木×3). */
    record Mosaic(StructuralNode element) implements CharacterComposition {}
}
