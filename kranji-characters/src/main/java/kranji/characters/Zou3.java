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

/** Characters pronounced zou (tone 3). */
public final class Zou3 {
    private Zou3() {}

    /** 走 (zou3) — walk; go. */
    public static final ChineseCharacterEntry 走_WALK_GO = entry("走")
            .py(Z, OPEN, Body.O, Tail.VOWEL_U, T3).strokes(7).radical(156)
            .topBottom(zi("土"), zi("止"))
            .compoundIndicative("walk; go");

    public static final List<ChineseCharacterEntry> ALL = List.of(走_WALK_GO);
}
