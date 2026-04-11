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

/** Characters pronounced ci (tone 4). */
public final class Ci4 {
    private Ci4() {}

    /** 次 (ci4) — time; order. */
    public static final ChineseCharacterEntry 次_TIME_ORDER = entry("次")
            .py(C, OPEN, Body.NULL, Tail.NONE, T4).strokes(6).radical(15)
            .leftRight(LIANG_DIAN_SHUI, zi("欠"))
            .phonoSemantic(LIANG_DIAN_SHUI, zi("欠"));

    /** 刺 (ci4) — thorn; stab. */
    public static final ChineseCharacterEntry 刺_THORN_STAB = entry("刺")
            .py(C, OPEN, Body.NULL, Tail.NONE, T4).strokes(8).radical(18)
            .leftRight(zi("朿"), LI_DAO_PANG)
            .phonoSemantic(LI_DAO_PANG, zi("朿"));

    public static final List<ChineseCharacterEntry> ALL = List.of(次_TIME_ORDER, 刺_THORN_STAB);
}
