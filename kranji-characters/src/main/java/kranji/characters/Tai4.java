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

/** Characters pronounced tai (tone 4). */
public final class Tai4 {
    private Tai4() {}

    /** 太 (tai4) — too; very. */
    public static final ChineseCharacterEntry 太_TOO_VERY = entry("太")
            .py(T, OPEN, Body.A, Tail.VOWEL_I, T4).strokes(4).radical(37)
            .singular()
            .indicative("too; very");

    /** 态 (tai4) — state; manner. */
    public static final ChineseCharacterEntry 态_STATE_MANNER = entry("态")
            .py(T, OPEN, Body.A, Tail.VOWEL_I, T4).strokes(8).radical(61)
            .topBottom(zi("太"), zi("心"))
            .phonoSemantic(zi("心"), zi("太"));

    public static final List<ChineseCharacterEntry> ALL = List.of(太_TOO_VERY, 态_STATE_MANNER);
}
