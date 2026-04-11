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

/** Characters pronounced fen (tone 4). */
public final class Fen4 {
    private Fen4() {}

    /** 份 (fen4) — portion; copy. */
    public static final ChineseCharacterEntry 份_PORTION_COPY = entry("份")
            .py(F, OPEN, Body.E, Tail.N, T4).strokes(6).radical(9)
            .leftRight(DAN_REN_PANG, zi("分"))
            .phonoSemantic(DAN_REN_PANG, zi("分"));

    /** 奋 (fen4) — strive; excite. */
    public static final ChineseCharacterEntry 奋_STRIVE_EXCITE = entry("奋")
            .py(F, OPEN, Body.E, Tail.N, T4).strokes(8).radical(37)
            .topBottom(zi("大"), zi("田"))
            .compoundIndicative("strive; excite");

    public static final List<ChineseCharacterEntry> ALL = List.of(份_PORTION_COPY, 奋_STRIVE_EXCITE);
}
