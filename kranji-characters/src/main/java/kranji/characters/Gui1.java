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

/** Characters pronounced gui (tone 1). */
public final class Gui1 {
    private Gui1() {}

    /** 规 (gui1) — rule; plan. */
    public static final ChineseCharacterEntry 规_RULE_PLAN = entry("规")
            .py(G, U, Body.E, Tail.VOWEL_I, T1).strokes(8).radical(147)
            .leftRight(zi("夫"), zi("见"))
            .phonoSemantic(zi("见"), zi("夫"));

    public static final List<ChineseCharacterEntry> ALL = List.of(规_RULE_PLAN);
}
