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

/** Characters pronounced de (tone 2). */
public final class De2 {
    private De2() {}

    /** 得 (de2) — get; obtain. */
    public static final ChineseCharacterEntry 得_GET_OBTAIN = entry("得")
            .py(D, OPEN, Body.E, Tail.NONE, T2).strokes(11).radical(60)
            .leftRight(SHUANG_REN_PANG, zi("旦"))
            .phonoSemantic(SHUANG_REN_PANG, zi("旦"));

    /** 德 (de2) — virtue; morals. */
    public static final ChineseCharacterEntry 德_VIRTUE_MORALS = entry("德")
            .py(D, OPEN, Body.E, Tail.NONE, T2).strokes(15).radical(60)
            .leftRight(SHUANG_REN_PANG, zi("惪"))
            .phonoSemantic(SHUANG_REN_PANG, zi("惪"));

    public static final List<ChineseCharacterEntry> ALL = List.of(得_GET_OBTAIN, 德_VIRTUE_MORALS);
}
