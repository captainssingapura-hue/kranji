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

/** Characters pronounced ci (tone 2). */
public final class Ci2 {
    private Ci2() {}

    /** 词 (ci2) — word; term. */
    public static final ChineseCharacterEntry 词_WORD_TERM = entry("词")
            .py(C, OPEN, Body.NULL, Tail.NONE, T2).strokes(7).radical(149)
            .leftRight(YAN_ZI_PANG, zi("司"))
            .phonoSemantic(YAN_ZI_PANG, zi("司"));

    public static final List<ChineseCharacterEntry> ALL = List.of(词_WORD_TERM);
}
