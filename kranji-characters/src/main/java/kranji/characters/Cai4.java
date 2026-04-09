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

/** Characters pronounced cai (tone 4). */
public final class Cai4 {
    private Cai4() {}

    /** 菜 (cai4) — vegetable. */
    public static final ChineseCharacterEntry 菜_VEGETABLE = entry("菜")
            .py(C, OPEN, Body.A, Tail.VOWEL_I, T4).strokes(11).radical(140)
            .topBottom(CAO_ZI_TOU, zi("采"))
            .phonoSemantic(CAO_ZI_TOU, zi("采"));

    public static final List<ChineseCharacterEntry> ALL = List.of(菜_VEGETABLE);
}
