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

/** Characters pronounced wo (tone 3). */
public final class Wo3 {
    private Wo3() {}

    /** 我 (wo3) — I; me. */
    public static final ChineseCharacterEntry 我_I_ME = entry("我")
            .py(ZERO, U, Body.O, Tail.NONE, T3).strokes(7).radical(62)
            .singular()
            .indicative("I; me");

    public static final List<ChineseCharacterEntry> ALL = List.of(我_I_ME);
}
