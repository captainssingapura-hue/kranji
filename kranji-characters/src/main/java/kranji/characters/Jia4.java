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

/** Characters pronounced jia (tone 4). */
public final class Jia4 {
    private Jia4() {}

    /** 价 (jia4) — price; value. */
    public static final ChineseCharacterEntry 价_PRICE_VALUE = entry("价")
            .py(J, I, Body.A, Tail.NONE, T4).strokes(6).radical(9)
            .leftRight(DAN_REN_PANG, zi("介"))
            .phonoSemantic(DAN_REN_PANG, zi("介"));

    public static final List<ChineseCharacterEntry> ALL = List.of(价_PRICE_VALUE);
}
