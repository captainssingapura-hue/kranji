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

/** Characters pronounced zhen (tone 4). */
public final class Zhen4 {
    private Zhen4() {}

    /** 震 (zhen4) — shake; shock. */
    public static final ChineseCharacterEntry 震_SHAKE_SHOCK = entry("震")
            .py(ZH, OPEN, Body.E, Tail.N, T4).strokes(15).radical(173)
            .topBottom(zi("雨"), zi("辰"))
            .phonoSemantic(zi("雨"), zi("辰"));

    public static final List<ChineseCharacterEntry> ALL = List.of(震_SHAKE_SHOCK);
}
