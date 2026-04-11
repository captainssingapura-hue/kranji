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

/** Characters pronounced jie (tone 1). */
public final class Jie1 {
    private Jie1() {}

    /** 接 (jie1) — receive; connect. */
    public static final ChineseCharacterEntry 接_RECEIVE_CONNECT = entry("接")
            .py(J, I, Body.E_CARON, Tail.NONE, T1).strokes(11).radical(64)
            .leftRight(TI_SHOU_PANG, zi("妾"))
            .phonoSemantic(TI_SHOU_PANG, zi("妾"));

    /** 阶 (jie1) — step; rank. */
    public static final ChineseCharacterEntry 阶_STEP_RANK = entry("阶")
            .py(J, I, Body.E_CARON, Tail.NONE, T1).strokes(6).radical(170)
            .leftRight(ZUO_ER_PANG, zi("介"))
            .phonoSemantic(ZUO_ER_PANG, zi("介"));

    public static final List<ChineseCharacterEntry> ALL = List.of(接_RECEIVE_CONNECT, 阶_STEP_RANK);
}
