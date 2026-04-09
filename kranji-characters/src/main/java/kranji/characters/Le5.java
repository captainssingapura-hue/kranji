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

/** Characters pronounced le (tone 5). */
public final class Le5 {
    private Le5() {}

    /** 了 (le5) — finish; modal. */
    public static final ChineseCharacterEntry 了_FINISH_MODAL = entry("了")
            .py(L, OPEN, Body.E, Tail.NONE, T5).strokes(2).radical(6)
            .singular()
            .pictograph();

    public static final List<ChineseCharacterEntry> ALL = List.of(了_FINISH_MODAL);
}
