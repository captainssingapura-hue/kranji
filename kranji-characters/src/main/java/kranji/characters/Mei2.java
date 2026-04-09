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

/** Characters pronounced mei (tone 2). */
public final class Mei2 {
    private Mei2() {}

    /** 没 (mei2) — not have. */
    public static final ChineseCharacterEntry 没_NOT_HAVE = entry("没")
            .py(M, OPEN, Body.E, Tail.VOWEL_I, T2).strokes(7).radical(85)
            .leftRight(SAN_DIAN_SHUI, zi("殳"))
            .phonoSemantic(SAN_DIAN_SHUI, zi("殳"));

    /** 梅 (mei2) — plum. */
    public static final ChineseCharacterEntry 梅_PLUM = entry("梅")
            .py(M, OPEN, Body.E, Tail.VOWEL_I, T2).strokes(11).radical(75)
            .leftRight(zi("木"), zi("每"))
            .phonoSemantic(zi("木"), zi("每"));

    public static final List<ChineseCharacterEntry> ALL = List.of(没_NOT_HAVE, 梅_PLUM);
}
