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

/** Characters pronounced gou (tone 4). */
public final class Gou4 {
    private Gou4() {}

    /** 构 (gou4) — construct. */
    public static final ChineseCharacterEntry 构_CONSTRUCT = entry("构")
            .py(G, OPEN, Body.O, Tail.VOWEL_U, T4).strokes(8).radical(75)
            .leftRight(zi("木"), zi("勾"))
            .phonoSemantic(zi("木"), zi("勾"));

    public static final List<ChineseCharacterEntry> ALL = List.of(构_CONSTRUCT);
}
