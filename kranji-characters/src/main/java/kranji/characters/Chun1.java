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

/** Characters pronounced chun (tone 1). */
public final class Chun1 {
    private Chun1() {}

    /** 春 (chun1) — spring. */
    public static final ChineseCharacterEntry 春_SPRING = entry("春")
            .py(CH, U, Body.E, Tail.N, T1).strokes(9).radical(72)
            .topBottom(zi("春"), zi("日"))
            .compoundIndicative("spring");

    public static final List<ChineseCharacterEntry> ALL = List.of(春_SPRING);
}
