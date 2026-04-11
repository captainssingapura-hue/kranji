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

/** Characters pronounced reng (tone 2). */
public final class Reng2 {
    private Reng2() {}

    /** 仍 (reng2) — still; yet. */
    public static final ChineseCharacterEntry 仍_STILL_YET = entry("仍")
            .py(R, OPEN, Body.E, Tail.NG, T2).strokes(4).radical(9)
            .leftRight(DAN_REN_PANG, zi("乃"))
            .phonoSemantic(DAN_REN_PANG, zi("乃"));

    public static final List<ChineseCharacterEntry> ALL = List.of(仍_STILL_YET);
}
