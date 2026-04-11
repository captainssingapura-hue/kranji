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

/** Characters pronounced ce (tone 4). */
public final class Ce4 {
    private Ce4() {}

    /** 测 (ce4) — measure; test. */
    public static final ChineseCharacterEntry 测_MEASURE_TEST = entry("测")
            .py(C, OPEN, Body.E, Tail.NONE, T4).strokes(9).radical(85)
            .leftRight(SAN_DIAN_SHUI, zi("则"))
            .phonoSemantic(SAN_DIAN_SHUI, zi("则"));

    /** 册 (ce4) — book; volume. */
    public static final ChineseCharacterEntry 册_BOOK_VOLUME = entry("册")
            .py(C, OPEN, Body.E, Tail.NONE, T4).strokes(5).radical(13)
            .singular()
            .pictograph();

    /** 厕 (ce4) — toilet. */
    public static final ChineseCharacterEntry 厕_TOILET = entry("厕")
            .py(C, OPEN, Body.E, Tail.NONE, T4).strokes(8).radical(27)
            .semiEnclosureUL(zi("厂"), zi("则"))
            .phonoSemantic(zi("厂"), zi("则"));

    public static final List<ChineseCharacterEntry> ALL = List.of(测_MEASURE_TEST, 册_BOOK_VOLUME, 厕_TOILET);
}
