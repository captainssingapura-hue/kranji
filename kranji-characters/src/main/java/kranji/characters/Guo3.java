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

/** Characters pronounced guo (tone 3). */
public final class Guo3 {
    private Guo3() {}

    /** 果 (guo3) — fruit; result. */
    public static final ChineseCharacterEntry 果_FRUIT_RESULT = entry("果")
            .py(G, U, Body.O, Tail.NONE, T3).strokes(8).radical(75)
            .topBottom(zi("田"), zi("木"))
            .compoundIndicative("fruit; result");

    public static final List<ChineseCharacterEntry> ALL = List.of(果_FRUIT_RESULT);
}
