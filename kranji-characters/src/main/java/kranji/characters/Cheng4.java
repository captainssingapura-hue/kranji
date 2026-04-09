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

/** Characters pronounced cheng (tone 4). */
public final class Cheng4 {
    private Cheng4() {}

    /** 秤 (cheng4) — scale; balance. */
    public static final ChineseCharacterEntry 秤_SCALE_BALANCE = entry("秤")
            .py(CH, OPEN, Body.E, Tail.NG, T4).strokes(10).radical(115)
            .leftRight(zi("禾"), zi("平"))
            .phonoSemantic(zi("禾"), zi("平"));

    public static final List<ChineseCharacterEntry> ALL = List.of(秤_SCALE_BALANCE);
}
