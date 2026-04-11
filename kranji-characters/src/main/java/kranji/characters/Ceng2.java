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

/** Characters pronounced ceng (tone 2). */
public final class Ceng2 {
    private Ceng2() {}

    /** 层 (ceng2) — layer; floor. */
    public static final ChineseCharacterEntry 层_LAYER_FLOOR = entry("层")
            .py(C, OPEN, Body.E, Tail.NG, T2).strokes(7).radical(44)
            .semiEnclosureUL(zi("尸"), zi("云"))
            .phonoSemantic(zi("尸"), zi("云"));

    public static final List<ChineseCharacterEntry> ALL = List.of(层_LAYER_FLOOR);
}
