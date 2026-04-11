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

/** Characters pronounced xie (tone 3). */
public final class Xie3 {
    private Xie3() {}

    /** 写 (xie3) — write. */
    public static final ChineseCharacterEntry 写_WRITE = entry("写")
            .py(X, I, Body.E_CARON, Tail.NONE, T3).strokes(5).radical(14)
            .topBottom(TU_BAO_GAI, zi("与"))
            .compoundIndicative("write");

    public static final List<ChineseCharacterEntry> ALL = List.of(写_WRITE);
}
