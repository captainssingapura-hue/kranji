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

/** Characters pronounced tou (tone 1). */
public final class Tou1 {
    private Tou1() {}

    /** 偷 (tou1) — steal. */
    public static final ChineseCharacterEntry 偷_STEAL = entry("偷")
            .py(T, OPEN, Body.O, Tail.VOWEL_U, T1).strokes(11).radical(9)
            .leftRight(DAN_REN_PANG, zi("俞"))
            .phonoSemantic(DAN_REN_PANG, zi("俞"));

    public static final List<ChineseCharacterEntry> ALL = List.of(偷_STEAL);
}
