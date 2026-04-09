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

/** Characters pronounced xi (tone 3). */
public final class Xi3 {
    private Xi3() {}

    /** 洗 (xi3) — wash. */
    public static final ChineseCharacterEntry 洗_WASH = entry("洗")
            .py(X, OPEN, Body.I, Tail.NONE, T3).strokes(9).radical(85)
            .leftRight(SAN_DIAN_SHUI, zi("先"))
            .phonoSemantic(SAN_DIAN_SHUI, zi("先"));

    /** 喜 (xi3) — happy; joy. */
    public static final ChineseCharacterEntry 喜_HAPPY_JOY = entry("喜")
            .py(X, OPEN, Body.I, Tail.NONE, T3).strokes(12).radical(30)
            .topBottom(zi("壴"), zi("口"))
            .compoundIndicative("happy; joy");

    public static final List<ChineseCharacterEntry> ALL = List.of(洗_WASH, 喜_HAPPY_JOY);
}
