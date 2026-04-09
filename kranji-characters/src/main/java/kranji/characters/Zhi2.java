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

/** Characters pronounced zhi (tone 2). */
public final class Zhi2 {
    private Zhi2() {}

    /** 直 (zhi2) — straight. */
    public static final ChineseCharacterEntry 直_STRAIGHT = entry("直")
            .py(ZH, OPEN, Body.NULL, Tail.NONE, T2).strokes(8).radical(109)
            .topBottom(zi("十"), zi("目"))
            .compoundIndicative("straight");

    /** 植 (zhi2) — plant; grow. */
    public static final ChineseCharacterEntry 植_PLANT_GROW = entry("植")
            .py(ZH, OPEN, Body.NULL, Tail.NONE, T2).strokes(12).radical(75)
            .leftRight(zi("木"), zi("直"))
            .phonoSemantic(zi("木"), zi("直"));

    /** 职 (zhi2) — post; duty. */
    public static final ChineseCharacterEntry 职_POST_DUTY = entry("职")
            .py(ZH, OPEN, Body.NULL, Tail.NONE, T2).strokes(11).radical(128)
            .leftRight(zi("耳"), zi("只"))
            .phonoSemantic(zi("耳"), zi("只"));

    /** 执 (zhi2) — execute; hold. */
    public static final ChineseCharacterEntry 执_EXECUTE_HOLD = entry("执")
            .py(ZH, OPEN, Body.NULL, Tail.NONE, T2).strokes(6).radical(64)
            .leftRight(TI_SHOU_PANG, zi("丸"))
            .phonoSemantic(TI_SHOU_PANG, zi("丸"));

    /** 值 (zhi2) — value; worth. */
    public static final ChineseCharacterEntry 值_VALUE_WORTH = entry("值")
            .py(ZH, OPEN, Body.NULL, Tail.NONE, T2).strokes(10).radical(9)
            .leftRight(DAN_REN_PANG, zi("直"))
            .phonoSemantic(DAN_REN_PANG, zi("直"));

    /** 侄 (zhi2) — nephew. */
    public static final ChineseCharacterEntry 侄_NEPHEW = entry("侄")
            .py(ZH, OPEN, Body.NULL, Tail.NONE, T2).strokes(8).radical(9)
            .leftRight(DAN_REN_PANG, zi("至"))
            .phonoSemantic(DAN_REN_PANG, zi("至"));

    public static final List<ChineseCharacterEntry> ALL = List.of(直_STRAIGHT, 植_PLANT_GROW, 职_POST_DUTY, 执_EXECUTE_HOLD, 值_VALUE_WORTH, 侄_NEPHEW);
}
