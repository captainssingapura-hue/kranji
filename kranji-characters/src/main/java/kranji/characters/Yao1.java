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

/** Characters pronounced yao (tone 1). */
public final class Yao1 {
    private Yao1() {}

    /** 腰 (yao1) — waist. */
    public static final ChineseCharacterEntry 腰_WAIST = entry("腰")
            .py(ZERO, I, Body.A, Tail.VOWEL_U, T1).strokes(13).radical(130)
            .leftRight(zi("月"), zi("要"))
            .phonoSemantic(zi("月"), zi("要"));

    public static final List<ChineseCharacterEntry> ALL = List.of(腰_WAIST);
}
