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

/** Characters pronounced zong (tone 3). */
public final class Zong3 {
    private Zong3() {}

    /** 总 (zong3) — total; always. */
    public static final ChineseCharacterEntry 总_TOTAL_ALWAYS = entry("总")
            .py(Z, OPEN, Body.O, Tail.NG, T3).strokes(9).radical(61)
            .topBottom(zi("丷"), zi("总"))
            .phonoSemantic(zi("心"), zi("总"));

    public static final List<ChineseCharacterEntry> ALL = List.of(总_TOTAL_ALWAYS);
}
