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

/** Characters pronounced po (tone 4). */
public final class Po4 {
    private Po4() {}

    /** 破 (po4) — break; torn. */
    public static final ChineseCharacterEntry 破_BREAK_TORN = entry("破")
            .py(P, OPEN, Body.O, Tail.NONE, T4).strokes(10).radical(112)
            .leftRight(zi("石"), zi("皮"))
            .phonoSemantic(zi("石"), zi("皮"));

    public static final List<ChineseCharacterEntry> ALL = List.of(破_BREAK_TORN);
}
