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

/** Characters pronounced suan (tone 4). */
public final class Suan4 {
    private Suan4() {}

    /** 算 (suan4) — calculate. */
    public static final ChineseCharacterEntry 算_CALCULATE = entry("算")
            .py(S, U, Body.A, Tail.N, T4).strokes(14).radical(118)
            .topBottom(ZHU_ZI_TOU, zi("算"))
            .phonoSemantic(ZHU_ZI_TOU, zi("算"));

    public static final List<ChineseCharacterEntry> ALL = List.of(算_CALCULATE);
}
