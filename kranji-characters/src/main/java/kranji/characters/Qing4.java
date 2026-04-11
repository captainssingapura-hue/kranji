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

/** Characters pronounced qing (tone 4). */
public final class Qing4 {
    private Qing4() {}

    /** 庆 (qing4) — celebrate. */
    public static final ChineseCharacterEntry 庆_CELEBRATE = entry("庆")
            .py(Q, OPEN, Body.I, Tail.NG, T4).strokes(6).radical(53)
            .semiEnclosureUL(zi("广"), zi("大"))
            .phonoSemantic(zi("广"), zi("大"));

    public static final List<ChineseCharacterEntry> ALL = List.of(庆_CELEBRATE);
}
