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

/** Characters pronounced ze (tone 2). */
public final class Ze2 {
    private Ze2() {}

    /** 则 (ze2) — then; rule. */
    public static final ChineseCharacterEntry 则_THEN_RULE = entry("则")
            .py(Z, OPEN, Body.E, Tail.NONE, T2).strokes(6).radical(18)
            .leftRight(zi("贝"), LI_DAO_PANG)
            .compoundIndicative("then; rule");

    /** 责 (ze2) — duty; blame. */
    public static final ChineseCharacterEntry 责_DUTY_BLAME = entry("责")
            .py(Z, OPEN, Body.E, Tail.NONE, T2).strokes(8).radical(154)
            .topBottom(zi("龶"), zi("贝"))
            .compoundIndicative("duty; blame");

    public static final List<ChineseCharacterEntry> ALL = List.of(则_THEN_RULE, 责_DUTY_BLAME);
}
