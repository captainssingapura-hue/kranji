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

/** Characters pronounced gan (tone 3). */
public final class Gan3 {
    private Gan3() {}

    /** 感 (gan3) — feel; sense. */
    public static final ChineseCharacterEntry 感_FEEL_SENSE = entry("感")
            .py(G, OPEN, Body.A, Tail.N, T3).strokes(13).radical(61)
            .topBottom(zi("咸"), zi("心"))
            .phonoSemantic(zi("心"), zi("咸"));

    /** 赶 (gan3) — hurry; catch up. */
    public static final ChineseCharacterEntry 赶_HURRY_CATCH_UP = entry("赶")
            .py(G, OPEN, Body.A, Tail.N, T3).strokes(10).radical(156)
            .semiEnclosureBL(zi("走"), zi("干"))
            .phonoSemantic(zi("走"), zi("干"));

    /** 敢 (gan3) — dare. */
    public static final ChineseCharacterEntry 敢_DARE = entry("敢")
            .py(G, OPEN, Body.A, Tail.N, T3).strokes(11).radical(66)
            .leftRight(zi("敢"), FAN_WEN_PANG)
            .compoundIndicative("dare");

    public static final List<ChineseCharacterEntry> ALL = List.of(感_FEEL_SENSE, 赶_HURRY_CATCH_UP, 敢_DARE);
}
