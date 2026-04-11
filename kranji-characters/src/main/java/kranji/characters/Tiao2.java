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

/** Characters pronounced tiao (tone 2). */
public final class Tiao2 {
    private Tiao2() {}

    /** 条 (tiao2) — strip; item. */
    public static final ChineseCharacterEntry 条_STRIP_ITEM = entry("条")
            .py(T, I, Body.A, Tail.VOWEL_U, T2).strokes(7).radical(75)
            .topBottom(zi("夂"), MU)
            .phonoSemantic(MU, zi("夂"));

    public static final List<ChineseCharacterEntry> ALL = List.of(条_STRIP_ITEM);
}
