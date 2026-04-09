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

/** Characters pronounced chong (tone 1). */
public final class Chong1 {
    private Chong1() {}

    /** 冲 (chong1) — rush; rinse. */
    public static final ChineseCharacterEntry 冲_RUSH_RINSE = entry("冲")
            .py(CH, OPEN, Body.O, Tail.NG, T1).strokes(6).radical(85)
            .leftRight(SAN_DIAN_SHUI, zi("中"))
            .phonoSemantic(SAN_DIAN_SHUI, zi("中"));

    /** 充 (chong1) — fill; charge. */
    public static final ChineseCharacterEntry 充_FILL_CHARGE = entry("充")
            .py(CH, OPEN, Body.O, Tail.NG, T1).strokes(6).radical(10)
            .topBottom(WEN_ZI_TOU, zi("允"))
            .compoundIndicative("fill; charge");

    public static final List<ChineseCharacterEntry> ALL = List.of(冲_RUSH_RINSE, 充_FILL_CHARGE);
}
