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

/** Characters pronounced chuan (tone 2). */
public final class Chuan2 {
    private Chuan2() {}

    /** 传 (chuan2) — pass on; spread. */
    public static final ChineseCharacterEntry 传_PASS_ON_SPREAD = entry("传")
            .py(CH, U, Body.A, Tail.N, T2).strokes(6).radical(9)
            .leftRight(DAN_REN_PANG, zi("专"))
            .phonoSemantic(DAN_REN_PANG, zi("专"));

    /** 船 (chuan2) — ship; boat. */
    public static final ChineseCharacterEntry 船_SHIP_BOAT = entry("船")
            .py(CH, U, Body.A, Tail.N, T2).strokes(11).radical(137)
            .leftRight(zi("舟"), zi("㕣"))
            .phonoSemantic(zi("舟"), zi("㕣"));

    public static final List<ChineseCharacterEntry> ALL = List.of(传_PASS_ON_SPREAD, 船_SHIP_BOAT);
}
