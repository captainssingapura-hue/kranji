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

/** Characters pronounced zhan (tone 4). */
public final class Zhan4 {
    private Zhan4() {}

    /** 战 (zhan4) — war; fight. */
    public static final ChineseCharacterEntry 战_WAR_FIGHT = entry("战")
            .py(ZH, OPEN, Body.A, Tail.N, T4).strokes(9).radical(62)
            .leftRight(zi("占"), zi("戈"))
            .phonoSemantic(zi("戈"), zi("占"));

    /** 站 (zhan4) — stand; station. */
    public static final ChineseCharacterEntry 站_STAND_STATION = entry("站")
            .py(ZH, OPEN, Body.A, Tail.N, T4).strokes(10).radical(117)
            .leftRight(zi("立"), zi("占"))
            .phonoSemantic(zi("立"), zi("占"));

    /** 占 (zhan4) — occupy. */
    public static final ChineseCharacterEntry 占_OCCUPY = entry("占")
            .py(ZH, OPEN, Body.A, Tail.N, T4).strokes(5).radical(25)
            .topBottom(zi("卜"), zi("口"))
            .compoundIndicative("occupy");

    public static final List<ChineseCharacterEntry> ALL = List.of(战_WAR_FIGHT, 站_STAND_STATION, 占_OCCUPY);
}
