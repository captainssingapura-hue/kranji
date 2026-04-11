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

/** Characters pronounced jiao (tone 1). */
public final class Jiao1 {
    private Jiao1() {}

    /** 浇 (jiao1) — water; pour. */
    public static final ChineseCharacterEntry 浇_WATER_POUR = entry("浇")
            .py(J, I, Body.A, Tail.VOWEL_U, T1).strokes(9).radical(85)
            .leftRight(SAN_DIAN_SHUI, zi("尧"))
            .phonoSemantic(SAN_DIAN_SHUI, zi("尧"));

    public static final List<ChineseCharacterEntry> ALL = List.of(浇_WATER_POUR);
}
