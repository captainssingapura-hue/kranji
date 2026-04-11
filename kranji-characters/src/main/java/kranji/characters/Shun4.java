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

/** Characters pronounced shun (tone 4). */
public final class Shun4 {
    private Shun4() {}

    /** 顺 (shun4) — along; smooth. */
    public static final ChineseCharacterEntry 顺_ALONG_SMOOTH = entry("顺")
            .py(SH, U, Body.E, Tail.N, T4).strokes(9).radical(181)
            .leftRight(zi("川"), zi("页"))
            .phonoSemantic(zi("页"), zi("川"));

    public static final List<ChineseCharacterEntry> ALL = List.of(顺_ALONG_SMOOTH);
}
