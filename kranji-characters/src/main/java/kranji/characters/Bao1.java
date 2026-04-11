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

/** Characters pronounced bao (tone 1). */
public final class Bao1 {
    private Bao1() {}

    /** 包 (bao1) — wrap; bag. */
    public static final ChineseCharacterEntry 包_WRAP_BAG = entry("包")
            .py(B, OPEN, Body.A, Tail.VOWEL_U, T1).strokes(5).radical(20)
            .semiEnclosureUR(BAO_ZI_TOU, zi("巳"))
            .compoundIndicative("wrap; bag");

    public static final List<ChineseCharacterEntry> ALL = List.of(包_WRAP_BAG);
}
