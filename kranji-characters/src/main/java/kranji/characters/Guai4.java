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

/** Characters pronounced guai (tone 4). */
public final class Guai4 {
    private Guai4() {}

    /** 怪 (guai4) — strange; blame. */
    public static final ChineseCharacterEntry 怪_STRANGE_BLAME = entry("怪")
            .py(G, U, Body.A, Tail.VOWEL_I, T4).strokes(8).radical(61)
            .leftRight(SHU_XIN_PANG, zi("圣"))
            .phonoSemantic(SHU_XIN_PANG, zi("圣"));

    public static final List<ChineseCharacterEntry> ALL = List.of(怪_STRANGE_BLAME);
}
