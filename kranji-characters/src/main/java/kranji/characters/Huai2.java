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

/** Characters pronounced huai (tone 2). */
public final class Huai2 {
    private Huai2() {}

    /** 怀 (huai2) — bosom; miss. */
    public static final ChineseCharacterEntry 怀_BOSOM_MISS = entry("怀")
            .py(H, U, Body.A, Tail.VOWEL_I, T2).strokes(7).radical(61)
            .leftRight(SHU_XIN_PANG, zi("不"))
            .phonoSemantic(SHU_XIN_PANG, zi("不"));

    public static final List<ChineseCharacterEntry> ALL = List.of(怀_BOSOM_MISS);
}
