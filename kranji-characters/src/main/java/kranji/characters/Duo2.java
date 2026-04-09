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

/** Characters pronounced duo (tone 2). */
public final class Duo2 {
    private Duo2() {}

    /** 夺 (duo2) — seize; compete. */
    public static final ChineseCharacterEntry 夺_SEIZE_COMPETE = entry("夺")
            .py(D, U, Body.O, Tail.NONE, T2).strokes(6).radical(37)
            .topBottom(zi("大"), zi("寸"))
            .compoundIndicative("seize; compete");

    public static final List<ChineseCharacterEntry> ALL = List.of(夺_SEIZE_COMPETE);
}
