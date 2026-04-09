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

/** Characters pronounced zheng (tone 4). */
public final class Zheng4 {
    private Zheng4() {}

    /** 正 (zheng4) — correct; right. */
    public static final ChineseCharacterEntry 正_CORRECT_RIGHT = entry("正")
            .py(ZH, OPEN, Body.E, Tail.NG, T4).strokes(5).radical(77)
            .singular()
            .indicative("correct; right");

    /** 政 (zheng4) — politics. */
    public static final ChineseCharacterEntry 政_POLITICS = entry("政")
            .py(ZH, OPEN, Body.E, Tail.NG, T4).strokes(8).radical(66)
            .leftRight(zi("正"), FAN_WEN_PANG)
            .phonoSemantic(FAN_WEN_PANG, zi("正"));

    public static final List<ChineseCharacterEntry> ALL = List.of(正_CORRECT_RIGHT, 政_POLITICS);
}
