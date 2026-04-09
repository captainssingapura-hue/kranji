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

/** Characters pronounced chou (tone 1). */
public final class Chou1 {
    private Chou1() {}

    /** 抽 (chou1) — draw; smoke. */
    public static final ChineseCharacterEntry 抽_DRAW_SMOKE = entry("抽")
            .py(CH, OPEN, Body.O, Tail.VOWEL_U, T1).strokes(8).radical(64)
            .leftRight(TI_SHOU_PANG, zi("由"))
            .phonoSemantic(TI_SHOU_PANG, zi("由"));

    public static final List<ChineseCharacterEntry> ALL = List.of(抽_DRAW_SMOKE);
}
