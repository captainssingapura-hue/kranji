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

/** Characters pronounced re (tone 4). */
public final class Re4 {
    private Re4() {}

    /** 热 (re4) — hot; heat. */
    public static final ChineseCharacterEntry 热_HOT_HEAT = entry("热")
            .py(R, OPEN, Body.E, Tail.NONE, T4).strokes(10).radical(86)
            .topBottom(zi("执"), SI_DIAN_DI)
            .phonoSemantic(SI_DIAN_DI, zi("执"));

    public static final List<ChineseCharacterEntry> ALL = List.of(热_HOT_HEAT);
}
