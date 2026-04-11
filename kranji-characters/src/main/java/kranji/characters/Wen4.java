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

/** Characters pronounced wen (tone 4). */
public final class Wen4 {
    private Wen4() {}

    /** 问 (wen4) — ask. */
    public static final ChineseCharacterEntry 问_ASK = entry("问")
            .py(ZERO, U, Body.E, Tail.N, T4).strokes(6).radical(30)
            .semiEnclosureT3(zi("门"), zi("口"))
            .compoundIndicative("ask");

    public static final List<ChineseCharacterEntry> ALL = List.of(问_ASK);
}
