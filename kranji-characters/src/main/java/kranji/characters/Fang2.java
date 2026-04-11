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

/** Characters pronounced fang (tone 2). */
public final class Fang2 {
    private Fang2() {}

    /** 防 (fang2) — prevent; guard. */
    public static final ChineseCharacterEntry 防_PREVENT_GUARD = entry("防")
            .py(F, OPEN, Body.A, Tail.NG, T2).strokes(6).radical(170)
            .leftRight(ZUO_ER_PANG, zi("方"))
            .phonoSemantic(ZUO_ER_PANG, zi("方"));

    /** 房 (fang2) — room; house. */
    public static final ChineseCharacterEntry 房_ROOM_HOUSE = entry("房")
            .py(F, OPEN, Body.A, Tail.NG, T2).strokes(8).radical(63)
            .semiEnclosureUL(zi("户"), zi("方"))
            .phonoSemantic(zi("户"), zi("方"));

    public static final List<ChineseCharacterEntry> ALL = List.of(防_PREVENT_GUARD, 房_ROOM_HOUSE);
}
