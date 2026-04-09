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

/** Characters pronounced cai (tone 2). */
public final class Cai2 {
    private Cai2() {}

    /** 才 (cai2) — talent; just. */
    public static final ChineseCharacterEntry 才_TALENT_JUST = entry("才")
            .py(C, OPEN, Body.A, Tail.VOWEL_I, T2).strokes(3).radical(64)
            .singular()
            .pictograph();

    /** 材 (cai2) — material. */
    public static final ChineseCharacterEntry 材_MATERIAL = entry("材")
            .py(C, OPEN, Body.A, Tail.VOWEL_I, T2).strokes(7).radical(75)
            .leftRight(zi("木"), zi("才"))
            .phonoSemantic(zi("木"), zi("才"));

    public static final List<ChineseCharacterEntry> ALL = List.of(才_TALENT_JUST, 材_MATERIAL);
}
