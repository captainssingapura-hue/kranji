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

/** Characters pronounced zhu (tone 4). */
public final class Zhu4 {
    private Zhu4() {}

    /** 住 (zhu4) — live; stay. */
    public static final ChineseCharacterEntry 住_LIVE_STAY = entry("住")
            .py(ZH, U, Body.U, Tail.NONE, T4).strokes(7).radical(9)
            .leftRight(DAN_REN_PANG, zi("主"))
            .phonoSemantic(DAN_REN_PANG, zi("主"));

    /** 注 (zhu4) — note; pour. */
    public static final ChineseCharacterEntry 注_NOTE_POUR = entry("注")
            .py(ZH, U, Body.U, Tail.NONE, T4).strokes(8).radical(85)
            .leftRight(SAN_DIAN_SHUI, zi("主"))
            .phonoSemantic(SAN_DIAN_SHUI, zi("主"));

    /** 助 (zhu4) — help; aid. */
    public static final ChineseCharacterEntry 助_HELP_AID = entry("助")
            .py(ZH, U, Body.U, Tail.NONE, T4).strokes(7).radical(19)
            .leftRight(zi("且"), zi("力"))
            .phonoSemantic(zi("力"), zi("且"));

    public static final List<ChineseCharacterEntry> ALL = List.of(住_LIVE_STAY, 注_NOTE_POUR, 助_HELP_AID);
}
