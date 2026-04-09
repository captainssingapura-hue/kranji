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

/** Characters pronounced dun (tone 4). */
public final class Dun4 {
    private Dun4() {}

    /** 顿 (dun4) — pause; meal. */
    public static final ChineseCharacterEntry 顿_PAUSE_MEAL = entry("顿")
            .py(D, U, Body.E, Tail.N, T4).strokes(10).radical(181)
            .leftRight(zi("屯"), zi("页"))
            .phonoSemantic(zi("页"), zi("屯"));

    /** 盾 (dun4) — shield. */
    public static final ChineseCharacterEntry 盾_SHIELD = entry("盾")
            .py(D, U, Body.E, Tail.N, T4).strokes(9).radical(109)
            .topBottom(zi("厂"), zi("目十"))
            .compoundIndicative("shield");

    public static final List<ChineseCharacterEntry> ALL = List.of(顿_PAUSE_MEAL, 盾_SHIELD);
}
