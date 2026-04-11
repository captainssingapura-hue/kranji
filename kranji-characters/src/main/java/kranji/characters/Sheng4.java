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

/** Characters pronounced sheng (tone 4). */
public final class Sheng4 {
    private Sheng4() {}

    /** 胜 (sheng4) — victory; excel. */
    public static final ChineseCharacterEntry 胜_VICTORY_EXCEL = entry("胜")
            .py(SH, OPEN, Body.E, Tail.NG, T4).strokes(9).radical(130)
            .leftRight(zi("月"), zi("生"))
            .phonoSemantic(zi("月"), zi("生"));

    public static final List<ChineseCharacterEntry> ALL = List.of(胜_VICTORY_EXCEL);
}
