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

/** Characters pronounced hu (tone 3). */
public final class Hu3 {
    private Hu3() {}

    /** 虎 (hu3) — tiger. */
    public static final ChineseCharacterEntry 虎_TIGER = entry("虎")
            .py(H, U, Body.U, Tail.NONE, T3).strokes(8).radical(141)
            .singular()
            .pictograph();

    public static final List<ChineseCharacterEntry> ALL = List.of(虎_TIGER);
}
