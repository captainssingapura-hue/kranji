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

/** Characters pronounced sao (tone 3). */
public final class Sao3 {
    private Sao3() {}

    /** 嫂 (sao3) — sister-in-law. */
    public static final ChineseCharacterEntry 嫂_SISTER_IN_LAW = entry("嫂")
            .py(S, OPEN, Body.A, Tail.VOWEL_U, T3).strokes(12).radical(38)
            .leftRight(zi("女"), zi("叟"))
            .phonoSemantic(zi("女"), zi("叟"));

    public static final List<ChineseCharacterEntry> ALL = List.of(嫂_SISTER_IN_LAW);
}
