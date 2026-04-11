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

/** Characters pronounced ding (tone 4). */
public final class Ding4 {
    private Ding4() {}

    /** 定 (ding4) — decide; fix. */
    public static final ChineseCharacterEntry 定_DECIDE_FIX = entry("定")
            .py(D, OPEN, Body.I, Tail.NG, T4).strokes(8).radical(40)
            .topBottom(BAO_GAI_TOU, zi("疋"))
            .phonoSemantic(BAO_GAI_TOU, zi("疋"));

    public static final List<ChineseCharacterEntry> ALL = List.of(定_DECIDE_FIX);
}
