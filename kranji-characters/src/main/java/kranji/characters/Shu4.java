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

/** Characters pronounced shu (tone 4). */
public final class Shu4 {
    private Shu4() {}

    /** 数 (shu4) — number; count. */
    public static final ChineseCharacterEntry 数_NUMBER_COUNT = entry("数")
            .py(SH, U, Body.U, Tail.NONE, T4).strokes(13).radical(66)
            .leftRight(zi("娄"), FAN_WEN_PANG)
            .phonoSemantic(FAN_WEN_PANG, zi("娄"));

    /** 术 (shu4) — art; technique. */
    public static final ChineseCharacterEntry 术_ART_TECHNIQUE = entry("术")
            .py(SH, U, Body.U, Tail.NONE, T4).strokes(5).radical(75)
            .singular()
            .pictograph();

    /** 树 (shu4) — tree. */
    public static final ChineseCharacterEntry 树_TREE = entry("树")
            .py(SH, U, Body.U, Tail.NONE, T4).strokes(9).radical(75)
            .leftRight(MU, zi("对"))
            .phonoSemantic(MU, zi("对"));

    /** 竖 (shu4) — vertical. */
    public static final ChineseCharacterEntry 竖_VERTICAL = entry("竖")
            .py(SH, U, Body.U, Tail.NONE, T4).strokes(13).radical(117)
            .topBottom(zi("刂立"), zi("又"))
            .compoundIndicative("vertical");

    public static final List<ChineseCharacterEntry> ALL = List.of(数_NUMBER_COUNT, 术_ART_TECHNIQUE, 树_TREE, 竖_VERTICAL);
}
