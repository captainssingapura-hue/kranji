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

/** Characters pronounced liu (tone 4). */
public final class Liu4 {
    private Liu4() {}

    /** 六 (liu4) — six. */
    public static final ChineseCharacterEntry 六_SIX = entry("六")
            .py(L, I, Body.O, Tail.VOWEL_U, T4).strokes(4).radical(12)
            .singular()
            .indicative("six");

    public static final List<ChineseCharacterEntry> ALL = List.of(六_SIX);
}
