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

/** Characters pronounced zhi (tone 3). */
public final class Zhi3 {
    private Zhi3() {}

    /** 只 (zhi3) — only. */
    public static final ChineseCharacterEntry 只_ONLY = entry("只")
            .py(ZH, OPEN, Body.NULL, Tail.NONE, T3).strokes(5).radical(30)
            .topBottom(zi("口"), zi("八"))
            .compoundIndicative("only");

    /** 指 (zhi3) — finger; point. */
    public static final ChineseCharacterEntry 指_FINGER_POINT = entry("指")
            .py(ZH, OPEN, Body.NULL, Tail.NONE, T3).strokes(9).radical(64)
            .leftRight(TI_SHOU_PANG, zi("旨"))
            .phonoSemantic(TI_SHOU_PANG, zi("旨"));

    /** 纸 (zhi3) — paper. */
    public static final ChineseCharacterEntry 纸_PAPER = entry("纸")
            .py(ZH, OPEN, Body.NULL, Tail.NONE, T3).strokes(7).radical(120)
            .leftRight(JIAO_SI_PANG, zi("氏"))
            .phonoSemantic(JIAO_SI_PANG, zi("氏"));

    public static final List<ChineseCharacterEntry> ALL = List.of(只_ONLY, 指_FINGER_POINT, 纸_PAPER);
}
