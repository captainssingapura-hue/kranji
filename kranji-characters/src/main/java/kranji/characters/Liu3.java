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

/** Characters pronounced liu (tone 3). */
public final class Liu3 {
    private Liu3() {}

    /** 柳 (liu3) — willow. */
    public static final ChineseCharacterEntry 柳_WILLOW = entry("柳")
            .py(L, I, Body.O, Tail.VOWEL_U, T3).strokes(9).radical(75)
            .leftRight(MU, zi("卯"))
            .phonoSemantic(MU, zi("卯"));

    public static final List<ChineseCharacterEntry> ALL = List.of(柳_WILLOW);
}
