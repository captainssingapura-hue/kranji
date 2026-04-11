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

/** Characters pronounced li (tone 4). */
public final class Li4 {
    private Li4() {}

    /** 力 (li4) — power; force. */
    public static final ChineseCharacterEntry 力_POWER_FORCE = entry("力")
            .py(L, OPEN, Body.I, Tail.NONE, T4).strokes(2).radical(19)
            .singular()
            .pictograph();

    /** 利 (li4) — benefit; sharp. */
    public static final ChineseCharacterEntry 利_BENEFIT_SHARP = entry("利")
            .py(L, OPEN, Body.I, Tail.NONE, T4).strokes(7).radical(18)
            .leftRight(zi("禾"), LI_DAO_PANG)
            .phonoSemantic(zi("禾"), LI_DAO_PANG);

    /** 立 (li4) — stand; set up. */
    public static final ChineseCharacterEntry 立_STAND_SET_UP = entry("立")
            .py(L, OPEN, Body.I, Tail.NONE, T4).strokes(5).radical(117)
            .singular()
            .pictograph();

    /** 历 (li4) — history; pass. */
    public static final ChineseCharacterEntry 历_HISTORY_PASS = entry("历")
            .py(L, OPEN, Body.I, Tail.NONE, T4).strokes(4).radical(27)
            .semiEnclosureUL(zi("厂"), zi("力"))
            .phonoSemantic(zi("厂"), zi("力"));

    /** 厉 (li4) — severe; fierce. */
    public static final ChineseCharacterEntry 厉_SEVERE_FIERCE = entry("厉")
            .py(L, OPEN, Body.I, Tail.NONE, T4).strokes(5).radical(27)
            .semiEnclosureUL(zi("厂"), zi("万"))
            .phonoSemantic(zi("厂"), zi("万"));

    public static final List<ChineseCharacterEntry> ALL = List.of(力_POWER_FORCE, 利_BENEFIT_SHARP, 立_STAND_SET_UP, 历_HISTORY_PASS, 厉_SEVERE_FIERCE);
}
