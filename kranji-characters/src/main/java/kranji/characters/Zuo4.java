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

/** Characters pronounced zuo (tone 4). */
public final class Zuo4 {
    private Zuo4() {}

    /** 作 (zuo4) — do; make. */
    public static final ChineseCharacterEntry 作_DO_MAKE = entry("作")
            .py(Z, U, Body.O, Tail.NONE, T4).strokes(7).radical(9)
            .leftRight(DAN_REN_PANG, zi("乍"))
            .phonoSemantic(DAN_REN_PANG, zi("乍"));

    /** 座 (zuo4) — seat; place. */
    public static final ChineseCharacterEntry 座_SEAT_PLACE = entry("座")
            .py(Z, U, Body.O, Tail.NONE, T4).strokes(10).radical(53)
            .semiEnclosureUL(zi("广"), zi("坐"))
            .phonoSemantic(zi("广"), zi("坐"));

    /** 做 (zuo4) — do; make. */
    public static final ChineseCharacterEntry 做_DO_MAKE = entry("做")
            .py(Z, U, Body.O, Tail.NONE, T4).strokes(11).radical(9)
            .leftMiddleRight(DAN_REN_PANG, zi("古"), FAN_WEN_PANG)
            .phonoSemantic(DAN_REN_PANG, zi("故"));

    public static final List<ChineseCharacterEntry> ALL = List.of(作_DO_MAKE, 座_SEAT_PLACE, 做_DO_MAKE);
}
