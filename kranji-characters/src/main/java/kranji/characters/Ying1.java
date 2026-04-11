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

/** Characters pronounced ying (tone 1). */
public final class Ying1 {
    private Ying1() {}

    /** 应 (ying1) — should; answer. */
    public static final ChineseCharacterEntry 应_SHOULD_ANSWER = entry("应")
            .py(ZERO, OPEN, Body.I, Tail.NG, T1).strokes(7).radical(53)
            .semiEnclosureUL(zi("广"), zi("应"))
            .phonoSemantic(zi("广"), zi("应"));

    public static final List<ChineseCharacterEntry> ALL = List.of(应_SHOULD_ANSWER);
}
