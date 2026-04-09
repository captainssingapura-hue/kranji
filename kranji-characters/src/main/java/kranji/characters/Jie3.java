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

/** Characters pronounced jie (tone 3). */
public final class Jie3 {
    private Jie3() {}

    /** 解 (jie3) — explain; solve. */
    public static final ChineseCharacterEntry 解_EXPLAIN_SOLVE = entry("解")
            .py(J, I, Body.E_CARON, Tail.NONE, T3).strokes(13).radical(148)
            .leftRight(zi("角"), zi("刀牛"))
            .compoundIndicative("explain; solve");

    /** 姐 (jie3) — older sister. */
    public static final ChineseCharacterEntry 姐_OLDER_SISTER = entry("姐")
            .py(J, I, Body.E_CARON, Tail.NONE, T3).strokes(8).radical(38)
            .leftRight(zi("女"), zi("且"))
            .phonoSemantic(zi("女"), zi("且"));

    public static final List<ChineseCharacterEntry> ALL = List.of(解_EXPLAIN_SOLVE, 姐_OLDER_SISTER);
}
