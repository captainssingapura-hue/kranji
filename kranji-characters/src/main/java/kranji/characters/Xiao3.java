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

/** Characters pronounced xiao (tone 3). */
public final class Xiao3 {
    private Xiao3() {}

    /** 小 (xiao3) — small. */
    public static final ChineseCharacterEntry 小_SMALL = entry("小")
            .py(X, I, Body.A, Tail.VOWEL_U, T3).strokes(3).radical(42)
            .singular()
            .pictograph();

    public static final List<ChineseCharacterEntry> ALL = List.of(小_SMALL);
}
