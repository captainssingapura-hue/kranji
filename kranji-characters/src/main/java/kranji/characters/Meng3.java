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

/** Characters pronounced meng (tone 3). */
public final class Meng3 {
    private Meng3() {}

    /** 猛 (meng3) — fierce; bold. */
    public static final ChineseCharacterEntry 猛_FIERCE_BOLD = entry("猛")
            .py(M, OPEN, Body.E, Tail.NG, T3).strokes(11).radical(94)
            .leftRight(FAN_QUAN_PANG, zi("孟"))
            .phonoSemantic(FAN_QUAN_PANG, zi("孟"));

    public static final List<ChineseCharacterEntry> ALL = List.of(猛_FIERCE_BOLD);
}
