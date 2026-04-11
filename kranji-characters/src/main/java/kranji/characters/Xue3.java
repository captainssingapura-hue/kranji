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

/** Characters pronounced xue (tone 3). */
public final class Xue3 {
    private Xue3() {}

    /** 雪 (xue3) — snow. */
    public static final ChineseCharacterEntry 雪_SNOW = entry("雪")
            .py(X, V, Body.E_CARON, Tail.NONE, T3).strokes(11).radical(173)
            .topBottom(zi("雨"), zi("彐"))
            .compoundIndicative("snow");

    public static final List<ChineseCharacterEntry> ALL = List.of(雪_SNOW);
}
