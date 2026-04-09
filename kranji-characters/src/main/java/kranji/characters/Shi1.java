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

/** Characters pronounced shi (tone 1). */
public final class Shi1 {
    private Shi1() {}

    /** 师 (shi1) — teacher; master. */
    public static final ChineseCharacterEntry 师_TEACHER_MASTER = entry("师")
            .py(SH, OPEN, Body.NULL, Tail.NONE, T1).strokes(6).radical(50)
            .leftRight(zi("帅"), zi("巾"))
            .compoundIndicative("teacher; master");

    /** 施 (shi1) — carry out; apply. */
    public static final ChineseCharacterEntry 施_CARRY_OUT_APPLY = entry("施")
            .py(SH, OPEN, Body.NULL, Tail.NONE, T1).strokes(9).radical(70)
            .leftRight(zi("方"), zi("也"))
            .phonoSemantic(zi("方"), zi("也"));

    /** 湿 (shi1) — wet; damp. */
    public static final ChineseCharacterEntry 湿_WET_DAMP = entry("湿")
            .py(SH, OPEN, Body.NULL, Tail.NONE, T1).strokes(12).radical(85)
            .leftRight(SAN_DIAN_SHUI, zi("显"))
            .phonoSemantic(SAN_DIAN_SHUI, zi("显"));

    public static final List<ChineseCharacterEntry> ALL = List.of(师_TEACHER_MASTER, 施_CARRY_OUT_APPLY, 湿_WET_DAMP);
}
