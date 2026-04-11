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

/** Characters pronounced she (tone 2). */
public final class She2 {
    private She2() {}

    /** 蛇 (she2) — snake. */
    public static final ChineseCharacterEntry 蛇_SNAKE = entry("蛇")
            .py(SH, OPEN, Body.E, Tail.NONE, T2).strokes(11).radical(142)
            .leftRight(zi("虫"), zi("它"))
            .phonoSemantic(zi("虫"), zi("它"));

    public static final List<ChineseCharacterEntry> ALL = List.of(蛇_SNAKE);
}
