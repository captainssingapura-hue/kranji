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

/** Characters pronounced li (tone 3). */
public final class Li3 {
    private Li3() {}

    /** 里 (li3) — inside; mile. */
    public static final ChineseCharacterEntry 里_INSIDE_MILE = entry("里")
            .py(L, OPEN, Body.I, Tail.NONE, T3).strokes(7).radical(166)
            .topBottom(zi("田"), zi("土"))
            .compoundIndicative("inside; mile");

    /** 理 (li3) — reason; logic. */
    public static final ChineseCharacterEntry 理_REASON_LOGIC = entry("理")
            .py(L, OPEN, Body.I, Tail.NONE, T3).strokes(11).radical(96)
            .leftRight(zi("王"), zi("里"))
            .phonoSemantic(zi("王"), zi("里"));

    public static final List<ChineseCharacterEntry> ALL = List.of(里_INSIDE_MILE, 理_REASON_LOGIC);
}
