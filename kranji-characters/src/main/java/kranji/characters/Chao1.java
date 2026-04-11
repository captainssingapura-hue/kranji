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

/** Characters pronounced chao (tone 1). */
public final class Chao1 {
    private Chao1() {}

    /** 超 (chao1) — super; exceed. */
    public static final ChineseCharacterEntry 超_SUPER_EXCEED = entry("超")
            .py(CH, OPEN, Body.A, Tail.VOWEL_U, T1).strokes(12).radical(156)
            .semiEnclosureBL(zi("走"), zi("召"))
            .phonoSemantic(zi("走"), zi("召"));

    public static final List<ChineseCharacterEntry> ALL = List.of(超_SUPER_EXCEED);
}
