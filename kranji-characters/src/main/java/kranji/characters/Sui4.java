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

/** Characters pronounced sui (tone 4). */
public final class Sui4 {
    private Sui4() {}

    /** 岁 (sui4) — age; year. */
    public static final ChineseCharacterEntry 岁_AGE_YEAR = entry("岁")
            .py(S, U, Body.E, Tail.VOWEL_I, T4).strokes(6).radical(46)
            .topBottom(zi("山"), zi("夕"))
            .compoundIndicative("age; year");

    public static final List<ChineseCharacterEntry> ALL = List.of(岁_AGE_YEAR);
}
