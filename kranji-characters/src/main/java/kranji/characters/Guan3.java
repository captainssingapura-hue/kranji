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

/** Characters pronounced guan (tone 3). */
public final class Guan3 {
    private Guan3() {}

    /** 管 (guan3) — manage; pipe. */
    public static final ChineseCharacterEntry 管_MANAGE_PIPE = entry("管")
            .py(G, U, Body.A, Tail.N, T3).strokes(14).radical(118)
            .topBottom(ZHU_ZI_TOU, zi("官"))
            .phonoSemantic(ZHU_ZI_TOU, zi("官"));

    /** 馆 (guan3) — building; hall. */
    public static final ChineseCharacterEntry 馆_BUILDING_HALL = entry("馆")
            .py(G, U, Body.A, Tail.N, T3).strokes(11).radical(184)
            .leftRight(SHI_ZI_PANG, zi("官"))
            .phonoSemantic(SHI_ZI_PANG, zi("官"));

    public static final List<ChineseCharacterEntry> ALL = List.of(管_MANAGE_PIPE, 馆_BUILDING_HALL);
}
