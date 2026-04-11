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

/** Characters pronounced dao (tone 4). */
public final class Dao4 {
    private Dao4() {}

    /** 到 (dao4) — arrive. */
    public static final ChineseCharacterEntry 到_ARRIVE = entry("到")
            .py(D, OPEN, Body.A, Tail.VOWEL_U, T4).strokes(8).radical(18)
            .leftRight(zi("至"), LI_DAO_PANG)
            .phonoSemantic(zi("至"), LI_DAO_PANG);

    /** 道 (dao4) — way; road. */
    public static final ChineseCharacterEntry 道_WAY_ROAD = entry("道")
            .py(D, OPEN, Body.A, Tail.VOWEL_U, T4).strokes(12).radical(162)
            .semiEnclosureBL(ZOU_ZHI_DI, zi("首"))
            .phonoSemantic(ZOU_ZHI_DI, zi("首"));

    public static final List<ChineseCharacterEntry> ALL = List.of(到_ARRIVE, 道_WAY_ROAD);
}
