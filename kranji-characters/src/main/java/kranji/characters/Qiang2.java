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

/** Characters pronounced qiang (tone 2). */
public final class Qiang2 {
    private Qiang2() {}

    /** 强 (qiang2) — strong. */
    public static final ChineseCharacterEntry 强_STRONG = entry("强")
            .py(Q, I, Body.A, Tail.NG, T2).strokes(12).radical(57)
            .leftRight(zi("弓"), zi("虽"))
            .phonoSemantic(zi("弓"), zi("虽"));

    /** 墙 (qiang2) — wall. */
    public static final ChineseCharacterEntry 墙_WALL = entry("墙")
            .py(Q, I, Body.A, Tail.NG, T2).strokes(14).radical(32)
            .leftRight(zi("土"), zi("啬"))
            .phonoSemantic(zi("土"), zi("啬"));

    public static final List<ChineseCharacterEntry> ALL = List.of(强_STRONG, 墙_WALL);
}
