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

/** Characters pronounced die (tone 2). */
public final class Die2 {
    private Die2() {}

    /** 蝶 (die2) — butterfly. */
    public static final ChineseCharacterEntry 蝶_BUTTERFLY = entry("蝶")
            .py(D, I, Body.E_CARON, Tail.NONE, T2).strokes(15).radical(142)
            .leftRight(zi("虫"), zi("枼"))
            .phonoSemantic(zi("虫"), zi("枼"));

    public static final List<ChineseCharacterEntry> ALL = List.of(蝶_BUTTERFLY);
}
