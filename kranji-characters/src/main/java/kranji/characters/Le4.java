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

/** Characters pronounced le (tone 4). */
public final class Le4 {
    private Le4() {}

    /** 乐 (le4) — happy; music. */
    public static final ChineseCharacterEntry 乐_HAPPY_MUSIC = entry("乐")
            .py(L, OPEN, Body.E, Tail.NONE, T4).strokes(5).radical(4)
            .singular()
            .pictograph();

    public static final List<ChineseCharacterEntry> ALL = List.of(乐_HAPPY_MUSIC);
}
