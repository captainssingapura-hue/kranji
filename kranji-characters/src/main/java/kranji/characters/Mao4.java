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

/** Characters pronounced mao (tone 4). */
public final class Mao4 {
    private Mao4() {}

    /** 帽 (mao4) — hat; cap. */
    public static final ChineseCharacterEntry 帽_HAT_CAP = entry("帽")
            .py(M, OPEN, Body.A, Tail.VOWEL_U, T4).strokes(12).radical(50)
            .leftRight(zi("巾"), zi("冒"))
            .phonoSemantic(zi("巾"), zi("冒"));

    /** 冒 (mao4) — emit; risk. */
    public static final ChineseCharacterEntry 冒_EMIT_RISK = entry("冒")
            .py(M, OPEN, Body.A, Tail.VOWEL_U, T4).strokes(9).radical(13)
            .topBottom(zi("冃"), zi("目"))
            .compoundIndicative("emit; risk");

    public static final List<ChineseCharacterEntry> ALL = List.of(帽_HAT_CAP, 冒_EMIT_RISK);
}
