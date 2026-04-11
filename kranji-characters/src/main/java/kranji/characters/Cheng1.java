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

/** Characters pronounced cheng (tone 1). */
public final class Cheng1 {
    private Cheng1() {}

    /** 称 (cheng1) — call; weigh. */
    public static final ChineseCharacterEntry 称_CALL_WEIGH = entry("称")
            .py(CH, OPEN, Body.E, Tail.NG, T1).strokes(10).radical(115)
            .leftRight(zi("禾"), zi("尔"))
            .phonoSemantic(zi("禾"), zi("尔"));

    public static final List<ChineseCharacterEntry> ALL = List.of(称_CALL_WEIGH);
}
