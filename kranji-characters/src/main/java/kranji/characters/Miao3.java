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

/** Characters pronounced miao (tone 3). */
public final class Miao3 {
    private Miao3() {}

    /** 秒 (miao3) — second (time). */
    public static final ChineseCharacterEntry 秒_SECOND_TIME = entry("秒")
            .py(M, I, Body.A, Tail.VOWEL_U, T3).strokes(9).radical(115)
            .leftRight(zi("禾"), zi("少"))
            .phonoSemantic(zi("禾"), zi("少"));

    public static final List<ChineseCharacterEntry> ALL = List.of(秒_SECOND_TIME);
}
