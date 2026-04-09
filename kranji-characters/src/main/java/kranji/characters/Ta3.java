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

/** Characters pronounced ta (tone 3). */
public final class Ta3 {
    private Ta3() {}

    /** 塔 (ta3) — tower; pagoda. */
    public static final ChineseCharacterEntry 塔_TOWER_PAGODA = entry("塔")
            .py(T, OPEN, Body.A, Tail.NONE, T3).strokes(12).radical(32)
            .leftRight(zi("土"), zi("荅"))
            .phonoSemantic(zi("土"), zi("荅"));

    public static final List<ChineseCharacterEntry> ALL = List.of(塔_TOWER_PAGODA);
}
