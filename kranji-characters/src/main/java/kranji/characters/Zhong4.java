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

/** Characters pronounced zhong (tone 4). */
public final class Zhong4 {
    private Zhong4() {}

    /** 重 (zhong4) — heavy; again. */
    public static final ChineseCharacterEntry 重_HEAVY_AGAIN = entry("重")
            .py(ZH, OPEN, Body.O, Tail.NG, T4).strokes(9).radical(166)
            .singular()
            .compoundIndicative("heavy; again");

    public static final List<ChineseCharacterEntry> ALL = List.of(重_HEAVY_AGAIN);
}
