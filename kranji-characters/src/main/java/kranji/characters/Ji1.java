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

/** Characters pronounced ji (tone 1). */
public final class Ji1 {
    private Ji1() {}

    /** 机 (ji1) — machine. */
    public static final ChineseCharacterEntry 机_MACHINE = entry("机")
            .py(J, OPEN, Body.I, Tail.NONE, T1).strokes(6).radical(75)
            .leftRight(zi("木"), zi("几"))
            .phonoSemantic(zi("木"), zi("几"));

    /** 基 (ji1) — base; basic. */
    public static final ChineseCharacterEntry 基_BASE_BASIC = entry("基")
            .py(J, OPEN, Body.I, Tail.NONE, T1).strokes(11).radical(32)
            .topBottom(zi("其"), zi("土"))
            .phonoSemantic(zi("土"), zi("其"));

    /** 鸡 (ji1) — chicken. */
    public static final ChineseCharacterEntry 鸡_CHICKEN = entry("鸡")
            .py(J, OPEN, Body.I, Tail.NONE, T1).strokes(7).radical(196)
            .leftRight(zi("又"), zi("鸟"))
            .phonoSemantic(zi("鸟"), zi("又"));

    /** 积 (ji1) — accumulate. */
    public static final ChineseCharacterEntry 积_ACCUMULATE = entry("积")
            .py(J, OPEN, Body.I, Tail.NONE, T1).strokes(10).radical(115)
            .leftRight(zi("禾"), zi("只"))
            .phonoSemantic(zi("禾"), zi("只"));

    /** 激 (ji1) — excite; fierce. */
    public static final ChineseCharacterEntry 激_EXCITE_FIERCE = entry("激")
            .py(J, OPEN, Body.I, Tail.NONE, T1).strokes(16).radical(85)
            .leftRight(SAN_DIAN_SHUI, zi("敫"))
            .phonoSemantic(SAN_DIAN_SHUI, zi("敫"));

    public static final List<ChineseCharacterEntry> ALL = List.of(机_MACHINE, 基_BASE_BASIC, 鸡_CHICKEN, 积_ACCUMULATE, 激_EXCITE_FIERCE);
}
