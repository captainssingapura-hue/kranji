package kranji.characters;

import kranji.entry.ChineseCharacterEntry;

import java.util.List;

import static kranji.characters.Comp.*;
import static kranji.characters.EntryBuilder.entry;
import static kranji.component.Parts.*;
import static kranji.pinyin.Initial.*;
import static kranji.pinyin.Head.*;
import kranji.pinyin.Body;
import kranji.pinyin.Tail;

/** Characters pronounced yin (tone 3). */
public final class Yin3 {
    private Yin3() {}

    /** 引 (yin3) — lead; draw. */
    public static final ChineseCharacterEntry 引_LEAD_DRAW = entry("引")
            .py(ZERO, OPEN, Body.I, Tail.N, T3).strokes(4).radical(57)
            .leftRight(zi("弓"), zi("丨"))
            .compoundIndicative("lead; draw");

    public static final List<ChineseCharacterEntry> ALL = List.of(引_LEAD_DRAW);
}
