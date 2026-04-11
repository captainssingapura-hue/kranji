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

/** Characters pronounced zhuan (tone 4). */
public final class Zhuan4 {
    private Zhuan4() {}

    /** 赚 (zhuan4) — earn; profit. */
    public static final ChineseCharacterEntry 赚_EARN_PROFIT = entry("赚")
            .py(ZH, U, Body.A, Tail.N, T4).strokes(14).radical(154)
            .leftRight(zi("贝"), zi("兼"))
            .phonoSemantic(zi("贝"), zi("兼"));

    public static final List<ChineseCharacterEntry> ALL = List.of(赚_EARN_PROFIT);
}
