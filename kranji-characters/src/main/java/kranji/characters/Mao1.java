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

/** Characters pronounced mao (tone 1). */
public final class Mao1 {
    private Mao1() {}

    /** 猫 (mao1) — cat. */
    public static final ChineseCharacterEntry 猫_CAT = entry("猫")
            .py(M, OPEN, Body.A, Tail.VOWEL_U, T1).strokes(11).radical(94)
            .leftRight(FAN_QUAN_PANG, zi("苗"))
            .phonoSemantic(FAN_QUAN_PANG, zi("苗"));

    public static final List<ChineseCharacterEntry> ALL = List.of(猫_CAT);
}
