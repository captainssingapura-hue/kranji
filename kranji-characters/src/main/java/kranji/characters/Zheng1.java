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

/** Characters pronounced zheng (tone 1). */
public final class Zheng1 {
    private Zheng1() {}

    /** 争 (zheng1) — compete; fight. */
    public static final ChineseCharacterEntry 争_COMPETE_FIGHT = entry("争")
            .py(ZH, OPEN, Body.E, Tail.NG, T1).strokes(6).radical(87)
            .singular()
            .compoundIndicative("compete; fight");

    /** 征 (zheng1) — journey; levy. */
    public static final ChineseCharacterEntry 征_JOURNEY_LEVY = entry("征")
            .py(ZH, OPEN, Body.E, Tail.NG, T1).strokes(8).radical(60)
            .leftRight(SHUANG_REN_PANG, zi("正"))
            .phonoSemantic(SHUANG_REN_PANG, zi("正"));

    public static final List<ChineseCharacterEntry> ALL = List.of(争_COMPETE_FIGHT, 征_JOURNEY_LEVY);
}
