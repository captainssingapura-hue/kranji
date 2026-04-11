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

/** Characters pronounced ke (tone 4). */
public final class Ke4 {
    private Ke4() {}

    /** 克 (ke4) — overcome; gram. */
    public static final ChineseCharacterEntry 克_OVERCOME_GRAM = entry("克")
            .py(K, OPEN, Body.E, Tail.NONE, T4).strokes(7).radical(10)
            .topBottom(zi("十"), zi("儿"))
            .compoundIndicative("overcome; gram");

    /** 客 (ke4) — guest. */
    public static final ChineseCharacterEntry 客_GUEST = entry("客")
            .py(K, OPEN, Body.E, Tail.NONE, T4).strokes(9).radical(40)
            .topBottom(BAO_GAI_TOU, zi("各"))
            .phonoSemantic(BAO_GAI_TOU, zi("各"));

    /** 刻 (ke4) — quarter; carve. */
    public static final ChineseCharacterEntry 刻_QUARTER_CARVE = entry("刻")
            .py(K, OPEN, Body.E, Tail.NONE, T4).strokes(8).radical(18)
            .leftRight(zi("亥"), LI_DAO_PANG)
            .phonoSemantic(LI_DAO_PANG, zi("亥"));

    public static final List<ChineseCharacterEntry> ALL = List.of(克_OVERCOME_GRAM, 客_GUEST, 刻_QUARTER_CARVE);
}
