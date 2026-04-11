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

/** Characters pronounced jiang (tone 1). */
public final class Jiang1 {
    private Jiang1() {}

    /** 将 (jiang1) — will; about to. */
    public static final ChineseCharacterEntry 将_WILL_ABOUT_TO = entry("将")
            .py(J, I, Body.A, Tail.NG, T1).strokes(9).radical(41)
            .leftRight(zi("丬"), zi("寸"))
            .phonoSemantic(zi("寸"), zi("丬"));

    public static final List<ChineseCharacterEntry> ALL = List.of(将_WILL_ABOUT_TO);
}
