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

/** Characters pronounced ti (tone 3). */
public final class Ti3 {
    private Ti3() {}

    /** 体 (ti3) — body. */
    public static final ChineseCharacterEntry 体_BODY = entry("体")
            .py(T, OPEN, Body.I, Tail.NONE, T3).strokes(7).radical(9)
            .leftRight(DAN_REN_PANG, zi("本"))
            .phonoSemantic(DAN_REN_PANG, zi("本"));

    public static final List<ChineseCharacterEntry> ALL = List.of(体_BODY);
}
