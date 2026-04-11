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

/** Characters pronounced liang (tone 4). */
public final class Liang4 {
    private Liang4() {}

    /** 量 (liang4) — amount; measure. */
    public static final ChineseCharacterEntry 量_AMOUNT_MEASURE = entry("量")
            .py(L, I, Body.A, Tail.NG, T4).strokes(12).radical(166)
            .topBottom(zi("旦"), zi("里"))
            .compoundIndicative("amount; measure");

    public static final List<ChineseCharacterEntry> ALL = List.of(量_AMOUNT_MEASURE);
}
