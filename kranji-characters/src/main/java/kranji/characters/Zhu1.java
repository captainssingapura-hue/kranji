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

/** Characters pronounced zhu (tone 1). */
public final class Zhu1 {
    private Zhu1() {}

    /** 株 (zhu1) — tree (mw). */
    public static final ChineseCharacterEntry 株_TREE_MW = entry("株")
            .py(ZH, U, Body.U, Tail.NONE, T1).strokes(10).radical(75)
            .leftRight(zi("木"), zi("朱"))
            .phonoSemantic(zi("木"), zi("朱"));

    /** 猪 (zhu1) — pig. */
    public static final ChineseCharacterEntry 猪_PIG = entry("猪")
            .py(ZH, U, Body.U, Tail.NONE, T1).strokes(11).radical(94)
            .leftRight(FAN_QUAN_PANG, zi("者"))
            .phonoSemantic(FAN_QUAN_PANG, zi("者"));

    public static final List<ChineseCharacterEntry> ALL = List.of(株_TREE_MW, 猪_PIG);
}
