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

/** Characters pronounced wu (tone 3). */
public final class Wu3 {
    private Wu3() {}

    /** 五 (wu3) — five. */
    public static final ChineseCharacterEntry 五_FIVE = entry("五")
            .py(ZERO, U, Body.U, Tail.NONE, T3).strokes(4).radical(7)
            .singular()
            .pictograph();

    /** 午 (wu3) — noon. */
    public static final ChineseCharacterEntry 午_NOON = entry("午")
            .py(ZERO, U, Body.U, Tail.NONE, T3).strokes(4).radical(24)
            .singular()
            .pictograph();

    /** 舞 (wu3) — dance. */
    public static final ChineseCharacterEntry 舞_DANCE = entry("舞")
            .py(ZERO, U, Body.U, Tail.NONE, T3).strokes(14).radical(136)
            .singular()
            .pictograph();

    public static final List<ChineseCharacterEntry> ALL = List.of(五_FIVE, 午_NOON, 舞_DANCE);
}
