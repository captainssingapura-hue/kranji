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

/** Characters pronounced zeng (tone 1). */
public final class Zeng1 {
    private Zeng1() {}

    /** 增 (zeng1) — increase. */
    public static final ChineseCharacterEntry 增_INCREASE = entry("增")
            .py(Z, OPEN, Body.E, Tail.NG, T1).strokes(15).radical(32)
            .leftRight(zi("土"), zi("曾"))
            .phonoSemantic(zi("土"), zi("曾"));

    public static final List<ChineseCharacterEntry> ALL = List.of(增_INCREASE);
}
