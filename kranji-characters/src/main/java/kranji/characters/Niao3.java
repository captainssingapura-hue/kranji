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

/** Characters pronounced niao (tone 3). */
public final class Niao3 {
    private Niao3() {}

    /** 鸟 (niao3) — bird. */
    public static final ChineseCharacterEntry 鸟_BIRD = entry("鸟")
            .py(N, I, Body.A, Tail.VOWEL_U, T3).strokes(5).radical(196)
            .singular()
            .pictograph();

    public static final List<ChineseCharacterEntry> ALL = List.of(鸟_BIRD);
}
