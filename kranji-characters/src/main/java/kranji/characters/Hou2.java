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

/** Characters pronounced hou (tone 2). */
public final class Hou2 {
    private Hou2() {}

    /** 猴 (hou2) — monkey. */
    public static final ChineseCharacterEntry 猴_MONKEY = entry("猴")
            .py(H, OPEN, Body.O, Tail.VOWEL_U, T2).strokes(12).radical(94)
            .leftRight(FAN_QUAN_PANG, zi("侯"))
            .phonoSemantic(FAN_QUAN_PANG, zi("侯"));

    public static final List<ChineseCharacterEntry> ALL = List.of(猴_MONKEY);
}
