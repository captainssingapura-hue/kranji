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

/** Characters pronounced huo (tone 4). */
public final class Huo4 {
    private Huo4() {}

    /** 获 (huo4) — gain; harvest. */
    public static final ChineseCharacterEntry 获_GAIN_HARVEST = entry("获")
            .py(H, U, Body.O, Tail.NONE, T4).strokes(10).radical(94)
            .topBottom(CAO_ZI_TOU, zi("获"))
            .phonoSemantic(FAN_QUAN_PANG, zi("获"));

    /** 货 (huo4) — goods; cargo. */
    public static final ChineseCharacterEntry 货_GOODS_CARGO = entry("货")
            .py(H, U, Body.O, Tail.NONE, T4).strokes(8).radical(154)
            .topBottom(zi("化"), zi("贝"))
            .phonoSemantic(zi("贝"), zi("化"));

    public static final List<ChineseCharacterEntry> ALL = List.of(获_GAIN_HARVEST, 货_GOODS_CARGO);
}
