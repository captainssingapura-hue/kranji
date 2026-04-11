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

/** Characters pronounced kuang (tone 4). */
public final class Kuang4 {
    private Kuang4() {}

    /** 况 (kuang4) — situation. */
    public static final ChineseCharacterEntry 况_SITUATION = entry("况")
            .py(K, U, Body.A, Tail.NG, T4).strokes(7).radical(85)
            .leftRight(SAN_DIAN_SHUI, zi("兄"))
            .phonoSemantic(SAN_DIAN_SHUI, zi("兄"));

    /** 矿 (kuang4) — mine; ore. */
    public static final ChineseCharacterEntry 矿_MINE_ORE = entry("矿")
            .py(K, U, Body.A, Tail.NG, T4).strokes(8).radical(112)
            .leftRight(zi("石"), zi("广"))
            .phonoSemantic(zi("石"), zi("广"));

    public static final List<ChineseCharacterEntry> ALL = List.of(况_SITUATION, 矿_MINE_ORE);
}
