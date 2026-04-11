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

/** Characters pronounced hang (tone 2). */
public final class Hang2 {
    private Hang2() {}

    /** 航 (hang2) — navigate; fly. */
    public static final ChineseCharacterEntry 航_NAVIGATE_FLY = entry("航")
            .py(H, OPEN, Body.A, Tail.NG, T2).strokes(10).radical(137)
            .leftRight(zi("舟"), zi("亢"))
            .phonoSemantic(zi("舟"), zi("亢"));

    public static final List<ChineseCharacterEntry> ALL = List.of(航_NAVIGATE_FLY);
}
