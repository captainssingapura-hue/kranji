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

/** Characters pronounced ya (tone 2). */
public final class Ya2 {
    private Ya2() {}

    /** 牙 (ya2) — tooth. */
    public static final ChineseCharacterEntry 牙_TOOTH = entry("牙")
            .py(ZERO, I, Body.A, Tail.NONE, T2).strokes(4).radical(92)
            .singular()
            .pictograph();

    public static final List<ChineseCharacterEntry> ALL = List.of(牙_TOOTH);
}
