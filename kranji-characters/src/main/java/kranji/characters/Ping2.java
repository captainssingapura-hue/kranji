package kranji.characters;

import kranji.entry.ChineseCharacterEntry;

import java.util.List;

import static kranji.characters.Comp.*;
import static kranji.characters.EntryBuilder.entry;
import static kranji.component.basic.BasicComponents.*;
import static kranji.pinyin.Initial.*;
import static kranji.pinyin.Head.*;
import kranji.pinyin.Body;
import kranji.pinyin.Tail;

/** Characters pronounced ping (tone 2). */
public final class Ping2 {
    private Ping2() {}

    /** 平 (ping2) — flat; peaceful. */
    public static final ChineseCharacterEntry 平_FLAT_PEACEFUL = entry("平")
            .py(P, OPEN, Body.I, Tail.NG, T2).strokes(5).radical(51)
            .singular()
            .indicative("flat; peaceful");

    /** 瓶 (ping2) — bottle. */
    public static final ChineseCharacterEntry 瓶_BOTTLE = entry("瓶")
            .py(P, OPEN, Body.I, Tail.NG, T2).strokes(10).radical(98)
            .leftRight(zi("并"), zi("瓦"))
            .phonoSemantic(zi("瓦"), zi("并"));

    /** 评 (ping2) — comment; review. */
    public static final ChineseCharacterEntry 评_COMMENT_REVIEW = entry("评")
            .py(P, OPEN, Body.I, Tail.NG, T2).strokes(7).radical(149)
            .leftRight(YAN_ZI_PANG, zi("平"))
            .phonoSemantic(YAN_ZI_PANG, zi("平"));

    public static final List<ChineseCharacterEntry> ALL = List.of(平_FLAT_PEACEFUL, 瓶_BOTTLE, 评_COMMENT_REVIEW);
}
