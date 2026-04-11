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

/** Characters pronounced chui (tone 1). */
public final class Chui1 {
    private Chui1() {}

    /** 吹 (chui1) — blow. */
    public static final ChineseCharacterEntry 吹_BLOW = entry("吹")
            .py(CH, U, Body.E, Tail.VOWEL_I, T1).strokes(7).radical(30)
            .leftRight(zi("口"), zi("欠"))
            .phonoSemantic(zi("口"), zi("欠"));

    public static final List<ChineseCharacterEntry> ALL = List.of(吹_BLOW);
}
