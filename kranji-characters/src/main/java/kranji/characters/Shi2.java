package kranji.characters;

import kranji.entry.ChineseCharacterEntry;

import java.util.List;

import static kranji.characters.Comp.*;
import static kranji.characters.EntryBuilder.entry;
import static kranji.component.Parts.*;
import static kranji.pinyin.Initial.*;
import static kranji.pinyin.Head.*;
import kranji.pinyin.Body;
import kranji.pinyin.Tail;

/** Characters pronounced shi (tone 2). */
public final class Shi2 {
    private Shi2() {}

    /** 时 (shi2) — time. */
    public static final ChineseCharacterEntry 时_TIME = entry("时")
            .py(SH, OPEN, Body.NULL, Tail.NONE, T2).strokes(7).radical(72)
            .leftRight(zi("日"), zi("寸"))
            .phonoSemantic(zi("日"), zi("寸"));

    /** 实 (shi2) — real; solid. */
    public static final ChineseCharacterEntry 实_REAL_SOLID = entry("实")
            .py(SH, OPEN, Body.NULL, Tail.NONE, T2).strokes(8).radical(40)
            .topBottom(BAO_GAI_TOU, zi("头"))
            .phonoSemantic(BAO_GAI_TOU, zi("头"));

    /** 十 (shi2) — ten. */
    public static final ChineseCharacterEntry 十_TEN = entry("十")
            .py(SH, OPEN, Body.NULL, Tail.NONE, T2).strokes(2).radical(24)
            .singular()
            .indicative("ten");

    /** 石 (shi2) — stone; rock. */
    public static final ChineseCharacterEntry 石_STONE_ROCK = entry("石")
            .py(SH, OPEN, Body.NULL, Tail.NONE, T2).strokes(5).radical(112)
            .singular()
            .pictograph();

    /** 食 (shi2) — food; eat. */
    public static final ChineseCharacterEntry 食_FOOD_EAT = entry("食")
            .py(SH, OPEN, Body.NULL, Tail.NONE, T2).strokes(9).radical(184)
            .singular()
            .pictograph();

    /** 识 (shi2) — know; recognize. */
    public static final ChineseCharacterEntry 识_KNOW_RECOGNIZE = entry("识")
            .py(SH, OPEN, Body.NULL, Tail.NONE, T2).strokes(7).radical(149)
            .leftRight(YAN_ZI_PANG, zi("只"))
            .phonoSemantic(YAN_ZI_PANG, zi("只"));

    public static final List<ChineseCharacterEntry> ALL = List.of(时_TIME, 实_REAL_SOLID, 十_TEN, 石_STONE_ROCK, 食_FOOD_EAT, 识_KNOW_RECOGNIZE);
}
