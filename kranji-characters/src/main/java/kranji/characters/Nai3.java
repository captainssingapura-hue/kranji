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

/** Characters pronounced nai (tone 3). */
public final class Nai3 {
    private Nai3() {}

    /** 奶 (nai3) — milk; grandma. */
    public static final ChineseCharacterEntry 奶_MILK_GRANDMA = entry("奶")
            .py(N, OPEN, Body.A, Tail.VOWEL_I, T3).strokes(5).radical(38)
            .leftRight(zi("女"), zi("乃"))
            .phonoSemantic(zi("女"), zi("乃"));

    public static final List<ChineseCharacterEntry> ALL = List.of(奶_MILK_GRANDMA);
}
