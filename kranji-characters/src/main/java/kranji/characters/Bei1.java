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

/** Characters pronounced bei (tone 1). */
public final class Bei1 {
    private Bei1() {}

    /** 杯 (bei1) — cup. */
    public static final ChineseCharacterEntry 杯_CUP = entry("杯")
            .py(B, OPEN, Body.E, Tail.VOWEL_I, T1).strokes(8).radical(75)
            .leftRight(MU, zi("不"))
            .phonoSemantic(MU, zi("不"));

    /** 碑 (bei1) — stele; tablet. */
    public static final ChineseCharacterEntry 碑_STELE_TABLET = entry("碑")
            .py(B, OPEN, Body.E, Tail.VOWEL_I, T1).strokes(13).radical(112)
            .leftRight(zi("石"), zi("卑"))
            .phonoSemantic(zi("石"), zi("卑"));

    /** 悲 (bei1) — sad; sorrow. */
    public static final ChineseCharacterEntry 悲_SAD_SORROW = entry("悲")
            .py(B, OPEN, Body.E, Tail.VOWEL_I, T1).strokes(12).radical(61)
            .topBottom(zi("非"), zi("心"))
            .phonoSemantic(zi("心"), zi("非"));

    public static final List<ChineseCharacterEntry> ALL = List.of(杯_CUP, 碑_STELE_TABLET, 悲_SAD_SORROW);
}
