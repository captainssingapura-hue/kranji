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

/** Characters pronounced ba (tone 3). */
public final class Ba3 {
    private Ba3() {}

    /** 把 (ba3) — grasp; handle. */
    public static final ChineseCharacterEntry 把_GRASP_HANDLE = entry("把")
            .py(B, OPEN, Body.A, Tail.NONE, T3).strokes(7).radical(64)
            .leftRight(TI_SHOU_PANG, zi("巴"))
            .phonoSemantic(TI_SHOU_PANG, zi("巴"));

    public static final List<ChineseCharacterEntry> ALL = List.of(把_GRASP_HANDLE);
}
