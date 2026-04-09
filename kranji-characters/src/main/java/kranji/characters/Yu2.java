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

/** Characters pronounced yu (tone 2). */
public final class Yu2 {
    private Yu2() {}

    /** 于 (yu2) — at; in. */
    public static final ChineseCharacterEntry 于_AT_IN = entry("于")
            .py(ZERO, V, Body.V, Tail.NONE, T2).strokes(3).radical(7)
            .singular()
            .pictograph();

    /** 鱼 (yu2) — fish. */
    public static final ChineseCharacterEntry 鱼_FISH = entry("鱼")
            .py(ZERO, V, Body.V, Tail.NONE, T2).strokes(8).radical(195)
            .singular()
            .pictograph();

    /** 余 (yu2) — surplus; remain. */
    public static final ChineseCharacterEntry 余_SURPLUS_REMAIN = entry("余")
            .py(ZERO, V, Body.V, Tail.NONE, T2).strokes(7).radical(9)
            .topBottom(zi("人"), zi("朩"))
            .compoundIndicative("surplus; remain");

    public static final List<ChineseCharacterEntry> ALL = List.of(于_AT_IN, 鱼_FISH, 余_SURPLUS_REMAIN);
}
