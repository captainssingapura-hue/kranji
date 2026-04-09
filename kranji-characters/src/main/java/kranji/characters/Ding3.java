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

/** Characters pronounced ding (tone 3). */
public final class Ding3 {
    private Ding3() {}

    /** 顶 (ding3) — top; peak. */
    public static final ChineseCharacterEntry 顶_TOP_PEAK = entry("顶")
            .py(D, OPEN, Body.I, Tail.NG, T3).strokes(8).radical(181)
            .leftRight(zi("丁"), zi("页"))
            .phonoSemantic(zi("页"), zi("丁"));

    public static final List<ChineseCharacterEntry> ALL = List.of(顶_TOP_PEAK);
}
