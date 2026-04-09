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

/** Characters pronounced dui (tone 1). */
public final class Dui1 {
    private Dui1() {}

    /** 堆 (dui1) — pile; heap. */
    public static final ChineseCharacterEntry 堆_PILE_HEAP = entry("堆")
            .py(D, U, Body.E, Tail.VOWEL_I, T1).strokes(11).radical(32)
            .leftRight(zi("土"), zi("隹"))
            .phonoSemantic(zi("土"), zi("隹"));

    public static final List<ChineseCharacterEntry> ALL = List.of(堆_PILE_HEAP);
}
