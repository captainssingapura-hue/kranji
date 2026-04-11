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

/** Characters pronounced shuo (tone 1). */
public final class Shuo1 {
    private Shuo1() {}

    /** 说 (shuo1) — say; speak. */
    public static final ChineseCharacterEntry 说_SAY_SPEAK = entry("说")
            .py(SH, U, Body.O, Tail.NONE, T1).strokes(9).radical(149)
            .leftRight(YAN_ZI_PANG, zi("兑"))
            .phonoSemantic(YAN_ZI_PANG, zi("兑"));

    public static final List<ChineseCharacterEntry> ALL = List.of(说_SAY_SPEAK);
}
