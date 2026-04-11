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

/** Characters pronounced deng (tone 3). */
public final class Deng3 {
    private Deng3() {}

    /** 等 (deng3) — wait; equal. */
    public static final ChineseCharacterEntry 等_WAIT_EQUAL = entry("等")
            .py(D, OPEN, Body.E, Tail.NG, T3).strokes(12).radical(118)
            .topBottom(ZHU_ZI_TOU, zi("寺"))
            .phonoSemantic(ZHU_ZI_TOU, zi("寺"));

    public static final List<ChineseCharacterEntry> ALL = List.of(等_WAIT_EQUAL);
}
