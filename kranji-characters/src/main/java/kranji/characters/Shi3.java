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

/** Characters pronounced shi (tone 3). */
public final class Shi3 {
    private Shi3() {}

    /** 使 (shi3) — make; use. */
    public static final ChineseCharacterEntry 使_MAKE_USE = entry("使")
            .py(SH, OPEN, Body.NULL, Tail.NONE, T3).strokes(8).radical(9)
            .leftRight(DAN_REN_PANG, zi("吏"))
            .phonoSemantic(DAN_REN_PANG, zi("吏"));

    /** 史 (shi3) — history. */
    public static final ChineseCharacterEntry 史_HISTORY = entry("史")
            .py(SH, OPEN, Body.NULL, Tail.NONE, T3).strokes(5).radical(30)
            .singular()
            .compoundIndicative("history");

    public static final List<ChineseCharacterEntry> ALL = List.of(使_MAKE_USE, 史_HISTORY);
}
