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

/** Characters pronounced bei (tone 4). */
public final class Bei4 {
    private Bei4() {}

    /** 被 (bei4) — by (passive). */
    public static final ChineseCharacterEntry 被_BY_PASSIVE = entry("被")
            .py(B, OPEN, Body.E, Tail.VOWEL_I, T4).strokes(10).radical(145)
            .leftRight(YI_ZI_PANG, zi("皮"))
            .phonoSemantic(YI_ZI_PANG, zi("皮"));

    /** 备 (bei4) — prepare; equip. */
    public static final ChineseCharacterEntry 备_PREPARE_EQUIP = entry("备")
            .py(B, OPEN, Body.E, Tail.VOWEL_I, T4).strokes(8).radical(34)
            .topBottom(zi("夂"), zi("田"))
            .compoundIndicative("prepare; equip");

    /** 背 (bei4) — back; carry. */
    public static final ChineseCharacterEntry 背_BACK_CARRY = entry("背")
            .py(B, OPEN, Body.E, Tail.VOWEL_I, T4).strokes(9).radical(130)
            .topBottom(zi("北"), zi("月"))
            .phonoSemantic(zi("月"), zi("北"));

    public static final List<ChineseCharacterEntry> ALL = List.of(被_BY_PASSIVE, 备_PREPARE_EQUIP, 背_BACK_CARRY);
}
