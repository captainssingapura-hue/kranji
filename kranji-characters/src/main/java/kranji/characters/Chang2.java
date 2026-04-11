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

/** Characters pronounced chang (tone 2). */
public final class Chang2 {
    private Chang2() {}

    /** 长 (chang2) — long; grow. */
    public static final ChineseCharacterEntry 长_LONG_GROW = entry("长")
            .py(CH, OPEN, Body.A, Tail.NG, T2).strokes(4).radical(168)
            .singular()
            .pictograph();

    /** 常 (chang2) — often; normal. */
    public static final ChineseCharacterEntry 常_OFTEN_NORMAL = entry("常")
            .py(CH, OPEN, Body.A, Tail.NG, T2).strokes(11).radical(50)
            .topBottom(zi("⺌"), zi("巾"))
            .phonoSemantic(zi("巾"), zi("尚"));

    /** 尝 (chang2) — taste; try. */
    public static final ChineseCharacterEntry 尝_TASTE_TRY = entry("尝")
            .py(CH, OPEN, Body.A, Tail.NG, T2).strokes(9).radical(42)
            .topBottom(zi("⺌"), zi("云"))
            .phonoSemantic(zi("⺌"), zi("尝"));

    public static final List<ChineseCharacterEntry> ALL = List.of(长_LONG_GROW, 常_OFTEN_NORMAL, 尝_TASTE_TRY);
}
