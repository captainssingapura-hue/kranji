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

/** Characters pronounced ling (tone 4). */
public final class Ling4 {
    private Ling4() {}

    /** 令 (ling4) — order; command. */
    public static final ChineseCharacterEntry 令_ORDER_COMMAND = entry("令")
            .py(L, OPEN, Body.I, Tail.NG, T4).strokes(5).radical(9)
            .topBottom(zi("人"), zi("卩"))
            .compoundIndicative("order; command");

    /** 另 (ling4) — other; another. */
    public static final ChineseCharacterEntry 另_OTHER_ANOTHER = entry("另")
            .py(L, OPEN, Body.I, Tail.NG, T4).strokes(5).radical(30)
            .topBottom(zi("口"), zi("力"))
            .compoundIndicative("other; another");

    public static final List<ChineseCharacterEntry> ALL = List.of(令_ORDER_COMMAND, 另_OTHER_ANOTHER);
}
