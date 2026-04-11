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

/** Characters pronounced chi (tone 1). */
public final class Chi1 {
    private Chi1() {}

    /** 吃 (chi1) — eat. */
    public static final ChineseCharacterEntry 吃_EAT = entry("吃")
            .py(CH, OPEN, Body.NULL, Tail.NONE, T1).strokes(6).radical(30)
            .leftRight(zi("口"), zi("乞"))
            .phonoSemantic(zi("口"), zi("乞"));

    public static final List<ChineseCharacterEntry> ALL = List.of(吃_EAT);
}
