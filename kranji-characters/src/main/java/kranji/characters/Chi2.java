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

/** Characters pronounced chi (tone 2). */
public final class Chi2 {
    private Chi2() {}

    /** 持 (chi2) — hold; maintain. */
    public static final ChineseCharacterEntry 持_HOLD_MAINTAIN = entry("持")
            .py(CH, OPEN, Body.NULL, Tail.NONE, T2).strokes(9).radical(64)
            .leftRight(TI_SHOU_PANG, zi("寺"))
            .phonoSemantic(TI_SHOU_PANG, zi("寺"));

    public static final List<ChineseCharacterEntry> ALL = List.of(持_HOLD_MAINTAIN);
}
