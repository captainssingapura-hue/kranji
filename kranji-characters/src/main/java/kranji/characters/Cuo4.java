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

/** Characters pronounced cuo (tone 4). */
public final class Cuo4 {
    private Cuo4() {}

    /** 错 (cuo4) — wrong; mistake. */
    public static final ChineseCharacterEntry 错_WRONG_MISTAKE = entry("错")
            .py(C, U, Body.O, Tail.NONE, T4).strokes(13).radical(167)
            .leftRight(JIN_ZI_PANG, zi("昔"))
            .phonoSemantic(JIN_ZI_PANG, zi("昔"));

    /** 措 (cuo4) — measure; step. */
    public static final ChineseCharacterEntry 措_MEASURE_STEP = entry("措")
            .py(C, U, Body.O, Tail.NONE, T4).strokes(11).radical(64)
            .leftRight(TI_SHOU_PANG, zi("昔"))
            .phonoSemantic(TI_SHOU_PANG, zi("昔"));

    public static final List<ChineseCharacterEntry> ALL = List.of(错_WRONG_MISTAKE, 措_MEASURE_STEP);
}
