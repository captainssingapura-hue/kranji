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

/** Characters pronounced qu (tone 1). */
public final class Qu1 {
    private Qu1() {}

    /** 区 (qu1) — area; district. */
    public static final ChineseCharacterEntry 区_AREA_DISTRICT = entry("区")
            .py(Q, V, Body.V, Tail.NONE, T1).strokes(4).radical(23)
            .semiEnclosureL3(SAN_KUANG, zi("㐅"))
            .compoundIndicative("area; district");

    /** 趋 (qu1) — tend toward. */
    public static final ChineseCharacterEntry 趋_TEND_TOWARD = entry("趋")
            .py(Q, V, Body.V, Tail.NONE, T1).strokes(12).radical(156)
            .semiEnclosureBL(zi("走"), zi("刍"))
            .phonoSemantic(zi("走"), zi("刍"));

    public static final List<ChineseCharacterEntry> ALL = List.of(区_AREA_DISTRICT, 趋_TEND_TOWARD);
}
