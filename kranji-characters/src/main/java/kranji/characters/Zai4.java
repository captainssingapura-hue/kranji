package kranji.characters;

import kranji.entry.ChineseCharacterEntry;

import java.util.List;

import static kranji.characters.Comp.*;
import static kranji.characters.EntryBuilder.entry;
import static kranji.component.Parts.*;
import static kranji.pinyin.Initial.*;
import static kranji.pinyin.Head.*;
import kranji.pinyin.Body;
import kranji.pinyin.Tail;

/** Characters pronounced zai (tone 4). */
public final class Zai4 {
    private Zai4() {}

    /** 在 (zai4) — at; in. */
    public static final ChineseCharacterEntry 在_AT_IN = entry("在")
            .py(Z, OPEN, Body.A, Tail.VOWEL_I, T4).strokes(6).radical(32)
            .semiEnclosureUL(zi("才"), zi("土"))
            .phonoSemantic(zi("土"), zi("才"));

    /** 再 (zai4) — again. */
    public static final ChineseCharacterEntry 再_AGAIN = entry("再")
            .py(Z, OPEN, Body.A, Tail.VOWEL_I, T4).strokes(6).radical(12)
            .singular()
            .indicative("again");

    /** 载 (zai4) — carry; record. */
    public static final ChineseCharacterEntry 载_CARRY_RECORD = entry("载")
            .py(Z, OPEN, Body.A, Tail.VOWEL_I, T4).strokes(10).radical(159)
            .semiEnclosureBL(zi("车"), zi("栽"))
            .phonoSemantic(zi("车"), zi("栽"));

    public static final List<ChineseCharacterEntry> ALL = List.of(在_AT_IN, 再_AGAIN, 载_CARRY_RECORD);
}
