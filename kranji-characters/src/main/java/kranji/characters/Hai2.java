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

/** Characters pronounced hai (tone 2). */
public final class Hai2 {
    private Hai2() {}

    /** 还 (hai2) — still; return. */
    public static final ChineseCharacterEntry 还_STILL_RETURN = entry("还")
            .py(H, OPEN, Body.A, Tail.VOWEL_I, T2).strokes(7).radical(162)
            .semiEnclosureBL(ZOU_ZHI_DI, zi("不"))
            .phonoSemantic(ZOU_ZHI_DI, zi("不"));

    public static final List<ChineseCharacterEntry> ALL = List.of(还_STILL_RETURN);
}
