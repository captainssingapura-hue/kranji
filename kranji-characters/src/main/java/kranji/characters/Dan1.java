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

/** Characters pronounced dan (tone 1). */
public final class Dan1 {
    private Dan1() {}

    /** 单 (dan1) — single; list. */
    public static final ChineseCharacterEntry 单_SINGLE_LIST = entry("单")
            .py(D, OPEN, Body.A, Tail.N, T1).strokes(8).radical(25)
            .singular()
            .pictograph();

    /** 担 (dan1) — carry; worry. */
    public static final ChineseCharacterEntry 担_CARRY_WORRY = entry("担")
            .py(D, OPEN, Body.A, Tail.N, T1).strokes(8).radical(64)
            .leftRight(TI_SHOU_PANG, zi("旦"))
            .phonoSemantic(TI_SHOU_PANG, zi("旦"));

    public static final List<ChineseCharacterEntry> ALL = List.of(单_SINGLE_LIST, 担_CARRY_WORRY);
}
