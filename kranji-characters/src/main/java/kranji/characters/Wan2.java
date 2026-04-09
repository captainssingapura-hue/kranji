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

/** Characters pronounced wan (tone 2). */
public final class Wan2 {
    private Wan2() {}

    /** 完 (wan2) — finish; whole. */
    public static final ChineseCharacterEntry 完_FINISH_WHOLE = entry("完")
            .py(ZERO, U, Body.A, Tail.N, T2).strokes(7).radical(40)
            .topBottom(BAO_GAI_TOU, zi("元"))
            .phonoSemantic(BAO_GAI_TOU, zi("元"));

    public static final List<ChineseCharacterEntry> ALL = List.of(完_FINISH_WHOLE);
}
