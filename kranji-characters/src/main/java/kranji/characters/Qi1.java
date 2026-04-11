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

/** Characters pronounced qi (tone 1). */
public final class Qi1 {
    private Qi1() {}

    /** 期 (qi1) — period; term. */
    public static final ChineseCharacterEntry 期_PERIOD_TERM = entry("期")
            .py(Q, OPEN, Body.I, Tail.NONE, T1).strokes(12).radical(74)
            .leftRight(zi("其"), zi("月"))
            .phonoSemantic(zi("月"), zi("其"));

    /** 七 (qi1) — seven. */
    public static final ChineseCharacterEntry 七_SEVEN = entry("七")
            .py(Q, OPEN, Body.I, Tail.NONE, T1).strokes(2).radical(1)
            .singular()
            .indicative("seven");

    /** 妻 (qi1) — wife. */
    public static final ChineseCharacterEntry 妻_WIFE = entry("妻")
            .py(Q, OPEN, Body.I, Tail.NONE, T1).strokes(8).radical(38)
            .singular()
            .compoundIndicative("wife");

    public static final List<ChineseCharacterEntry> ALL = List.of(期_PERIOD_TERM, 七_SEVEN, 妻_WIFE);
}
