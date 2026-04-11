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

/** Characters pronounced tai (tone 2). */
public final class Tai2 {
    private Tai2() {}

    /** 台 (tai2) — platform. */
    public static final ChineseCharacterEntry 台_PLATFORM = entry("台")
            .py(T, OPEN, Body.A, Tail.VOWEL_I, T2).strokes(5).radical(30)
            .topBottom(zi("厶"), zi("口"))
            .phonoSemantic(zi("口"), zi("厶"));

    public static final List<ChineseCharacterEntry> ALL = List.of(台_PLATFORM);
}
