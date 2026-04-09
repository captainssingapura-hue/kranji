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

/** Characters pronounced lie (tone 4). */
public final class Lie4 {
    private Lie4() {}

    /** 列 (lie4) — list; row. */
    public static final ChineseCharacterEntry 列_LIST_ROW = entry("列")
            .py(L, I, Body.E_CARON, Tail.NONE, T4).strokes(6).radical(18)
            .leftRight(zi("歹"), LI_DAO_PANG)
            .phonoSemantic(zi("歹"), LI_DAO_PANG);

    /** 裂 (lie4) — split; crack. */
    public static final ChineseCharacterEntry 裂_SPLIT_CRACK = entry("裂")
            .py(L, I, Body.E_CARON, Tail.NONE, T4).strokes(12).radical(145)
            .topBottom(zi("列"), zi("衣"))
            .phonoSemantic(zi("衣"), zi("列"));

    public static final List<ChineseCharacterEntry> ALL = List.of(列_LIST_ROW, 裂_SPLIT_CRACK);
}
