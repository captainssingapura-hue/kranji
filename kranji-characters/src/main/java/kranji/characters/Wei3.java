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

/** Characters pronounced wei (tone 3). */
public final class Wei3 {
    private Wei3() {}

    /** 委 (wei3) — entrust. */
    public static final ChineseCharacterEntry 委_ENTRUST = entry("委")
            .py(ZERO, U, Body.E, Tail.VOWEL_I, T3).strokes(8).radical(38)
            .topBottom(zi("禾"), zi("女"))
            .compoundIndicative("entrust");

    public static final List<ChineseCharacterEntry> ALL = List.of(委_ENTRUST);
}
