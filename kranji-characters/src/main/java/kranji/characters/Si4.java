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

/** Characters pronounced si (tone 4). */
public final class Si4 {
    private Si4() {}

    /** 四 (si4) — four. */
    public static final ChineseCharacterEntry 四_FOUR = entry("四")
            .py(S, OPEN, Body.NULL, Tail.NONE, T4).strokes(5).radical(31)
            .fullEnclosure(GUO_ZI_KUANG, zi("八"))
            .pictograph();

    /** 寺 (si4) — temple. */
    public static final ChineseCharacterEntry 寺_TEMPLE = entry("寺")
            .py(S, OPEN, Body.NULL, Tail.NONE, T4).strokes(6).radical(41)
            .topBottom(zi("土"), zi("寸"))
            .compoundIndicative("temple");

    public static final List<ChineseCharacterEntry> ALL = List.of(四_FOUR, 寺_TEMPLE);
}
