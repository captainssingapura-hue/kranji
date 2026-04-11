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

/** Characters pronounced tui (tone 4). */
public final class Tui4 {
    private Tui4() {}

    /** 退 (tui4) — retreat; return. */
    public static final ChineseCharacterEntry 退_RETREAT_RETURN = entry("退")
            .py(T, U, Body.E, Tail.VOWEL_I, T4).strokes(9).radical(162)
            .semiEnclosureBL(ZOU_ZHI_DI, zi("退"))
            .compoundIndicative("retreat; return");

    public static final List<ChineseCharacterEntry> ALL = List.of(退_RETREAT_RETURN);
}
