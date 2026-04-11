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

/** Characters pronounced ji (tone 4). */
public final class Ji4 {
    private Ji4() {}

    /** 记 (ji4) — record; remember. */
    public static final ChineseCharacterEntry 记_RECORD_REMEMBER = entry("记")
            .py(J, OPEN, Body.I, Tail.NONE, T4).strokes(5).radical(149)
            .leftRight(YAN_ZI_PANG, zi("己"))
            .phonoSemantic(YAN_ZI_PANG, zi("己"));

    /** 计 (ji4) — plan; count. */
    public static final ChineseCharacterEntry 计_PLAN_COUNT = entry("计")
            .py(J, OPEN, Body.I, Tail.NONE, T4).strokes(4).radical(149)
            .leftRight(YAN_ZI_PANG, zi("十"))
            .phonoSemantic(YAN_ZI_PANG, zi("十"));

    /** 济 (ji4) — aid; economy. */
    public static final ChineseCharacterEntry 济_AID_ECONOMY = entry("济")
            .py(J, OPEN, Body.I, Tail.NONE, T4).strokes(9).radical(85)
            .leftRight(SAN_DIAN_SHUI, zi("齐"))
            .phonoSemantic(SAN_DIAN_SHUI, zi("齐"));

    /** 技 (ji4) — skill; technique. */
    public static final ChineseCharacterEntry 技_SKILL_TECHNIQUE = entry("技")
            .py(J, OPEN, Body.I, Tail.NONE, T4).strokes(7).radical(64)
            .leftRight(TI_SHOU_PANG, zi("支"))
            .phonoSemantic(TI_SHOU_PANG, zi("支"));

    /** 际 (ji4) — occasion; border. */
    public static final ChineseCharacterEntry 际_OCCASION_BORDER = entry("际")
            .py(J, OPEN, Body.I, Tail.NONE, T4).strokes(7).radical(170)
            .leftRight(ZUO_ER_PANG, zi("示"))
            .phonoSemantic(ZUO_ER_PANG, zi("示"));

    /** 迹 (ji4) — trace; mark. */
    public static final ChineseCharacterEntry 迹_TRACE_MARK = entry("迹")
            .py(J, OPEN, Body.I, Tail.NONE, T4).strokes(9).radical(162)
            .semiEnclosureBL(ZOU_ZHI_DI, zi("亦"))
            .phonoSemantic(ZOU_ZHI_DI, zi("亦"));

    /** 纪 (ji4) — era; record. */
    public static final ChineseCharacterEntry 纪_ERA_RECORD = entry("纪")
            .py(J, OPEN, Body.I, Tail.NONE, T4).strokes(6).radical(120)
            .leftRight(JIAO_SI_PANG, zi("己"))
            .phonoSemantic(JIAO_SI_PANG, zi("己"));

    public static final List<ChineseCharacterEntry> ALL = List.of(记_RECORD_REMEMBER, 计_PLAN_COUNT, 济_AID_ECONOMY, 技_SKILL_TECHNIQUE, 际_OCCASION_BORDER, 迹_TRACE_MARK, 纪_ERA_RECORD);
}
