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

/** Characters pronounced ju (tone 3). */
public final class Ju3 {
    private Ju3() {}

    /** 举 (ju3) — lift; raise. */
    public static final ChineseCharacterEntry 举_LIFT_RAISE = entry("举")
            .py(J, V, Body.V, Tail.NONE, T3).strokes(9).radical(3)
            .topBottom(zi("兴"), zi("手"))
            .compoundIndicative("lift; raise");

    public static final List<ChineseCharacterEntry> ALL = List.of(举_LIFT_RAISE);
}
