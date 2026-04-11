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

/** Characters pronounced xian (tone 3). */
public final class Xian3 {
    private Xian3() {}

    /** 显 (xian3) — apparent; show. */
    public static final ChineseCharacterEntry 显_APPARENT_SHOW = entry("显")
            .py(X, I, Body.A, Tail.N, T3).strokes(9).radical(72)
            .topBottom(zi("日"), zi("业"))
            .compoundIndicative("apparent; show");

    public static final List<ChineseCharacterEntry> ALL = List.of(显_APPARENT_SHOW);
}
