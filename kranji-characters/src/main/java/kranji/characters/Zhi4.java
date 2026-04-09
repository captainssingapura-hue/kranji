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

/** Characters pronounced zhi (tone 4). */
public final class Zhi4 {
    private Zhi4() {}

    /** 制 (zhi4) — system; make. */
    public static final ChineseCharacterEntry 制_SYSTEM_MAKE = entry("制")
            .py(ZH, OPEN, Body.NULL, Tail.NONE, T4).strokes(8).radical(18)
            .leftRight(zi("制"), LI_DAO_PANG)
            .phonoSemantic(LI_DAO_PANG, zi("制"));

    /** 治 (zhi4) — govern; cure. */
    public static final ChineseCharacterEntry 治_GOVERN_CURE = entry("治")
            .py(ZH, OPEN, Body.NULL, Tail.NONE, T4).strokes(8).radical(85)
            .leftRight(SAN_DIAN_SHUI, zi("台"))
            .phonoSemantic(SAN_DIAN_SHUI, zi("台"));

    /** 志 (zhi4) — will; aspire. */
    public static final ChineseCharacterEntry 志_WILL_ASPIRE = entry("志")
            .py(ZH, OPEN, Body.NULL, Tail.NONE, T4).strokes(7).radical(61)
            .topBottom(zi("士"), zi("心"))
            .phonoSemantic(zi("心"), zi("士"));

    /** 至 (zhi4) — arrive; to. */
    public static final ChineseCharacterEntry 至_ARRIVE_TO = entry("至")
            .py(ZH, OPEN, Body.NULL, Tail.NONE, T4).strokes(6).radical(133)
            .singular()
            .pictograph();

    /** 致 (zhi4) — cause; send. */
    public static final ChineseCharacterEntry 致_CAUSE_SEND = entry("致")
            .py(ZH, OPEN, Body.NULL, Tail.NONE, T4).strokes(10).radical(133)
            .leftRight(zi("至"), FAN_WEN_PANG)
            .phonoSemantic(FAN_WEN_PANG, zi("至"));

    /** 智 (zhi4) — wisdom; smart. */
    public static final ChineseCharacterEntry 智_WISDOM_SMART = entry("智")
            .py(ZH, OPEN, Body.NULL, Tail.NONE, T4).strokes(12).radical(72)
            .topBottom(zi("知"), zi("日"))
            .phonoSemantic(zi("日"), zi("知"));

    public static final List<ChineseCharacterEntry> ALL = List.of(制_SYSTEM_MAKE, 治_GOVERN_CURE, 志_WILL_ASPIRE, 至_ARRIVE_TO, 致_CAUSE_SEND, 智_WISDOM_SMART);
}
