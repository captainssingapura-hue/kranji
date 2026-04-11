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

/** Characters pronounced nai (tone 4). */
public final class Nai4 {
    private Nai4() {}

    /** 耐 (nai4) — endure; patient. */
    public static final ChineseCharacterEntry 耐_ENDURE_PATIENT = entry("耐")
            .py(N, OPEN, Body.A, Tail.VOWEL_I, T4).strokes(9).radical(126)
            .leftRight(zi("而"), zi("寸"))
            .compoundIndicative("endure; patient");

    public static final List<ChineseCharacterEntry> ALL = List.of(耐_ENDURE_PATIENT);
}
