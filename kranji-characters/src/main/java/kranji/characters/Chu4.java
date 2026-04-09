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

/** Characters pronounced chu (tone 4). */
public final class Chu4 {
    private Chu4() {}

    /** 处 (chu4) — place; handle. */
    public static final ChineseCharacterEntry 处_PLACE_HANDLE = entry("处")
            .py(CH, U, Body.U, Tail.NONE, T4).strokes(5).radical(34)
            .semiEnclosureUL(zi("夂"), zi("卜"))
            .compoundIndicative("place; handle");

    public static final List<ChineseCharacterEntry> ALL = List.of(处_PLACE_HANDLE);
}
