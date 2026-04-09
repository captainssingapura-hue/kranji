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

/** Characters pronounced yao (tone 4). */
public final class Yao4 {
    private Yao4() {}

    /** 要 (yao4) — want; need. */
    public static final ChineseCharacterEntry 要_WANT_NEED = entry("要")
            .py(ZERO, I, Body.A, Tail.VOWEL_U, T4).strokes(9).radical(146)
            .topBottom(zi("覀"), zi("女"))
            .compoundIndicative("want; need");

    /** 药 (yao4) — medicine; drug. */
    public static final ChineseCharacterEntry 药_MEDICINE_DRUG = entry("药")
            .py(ZERO, I, Body.A, Tail.VOWEL_U, T4).strokes(9).radical(140)
            .topBottom(CAO_ZI_TOU, zi("约"))
            .phonoSemantic(CAO_ZI_TOU, zi("约"));

    public static final List<ChineseCharacterEntry> ALL = List.of(要_WANT_NEED, 药_MEDICINE_DRUG);
}
