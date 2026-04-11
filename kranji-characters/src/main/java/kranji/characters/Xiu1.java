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

/** Characters pronounced xiu (tone 1). */
public final class Xiu1 {
    private Xiu1() {}

    /** 休 (xiu) — rest. LeftRight: 亻 + 木. Compound indicative. */
    public static final ChineseCharacterEntry 休_REST = entry("休")
            .py(X, I, Body.O, Tail.VOWEL_U, T1).strokes(6).radical(9)
            .leftRight(DAN_REN_PANG, MU)
            .compoundIndicative("亻(person) + 木(tree) → person leaning on tree → rest");

    public static final List<ChineseCharacterEntry> ALL = List.of(休_REST);
}
