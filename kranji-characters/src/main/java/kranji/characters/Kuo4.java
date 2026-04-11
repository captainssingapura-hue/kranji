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

/** Characters pronounced kuo (tone 4). */
public final class Kuo4 {
    private Kuo4() {}

    /** 扩 (kuo4) — expand; enlarge. */
    public static final ChineseCharacterEntry 扩_EXPAND_ENLARGE = entry("扩")
            .py(K, U, Body.O, Tail.NONE, T4).strokes(6).radical(64)
            .leftRight(TI_SHOU_PANG, zi("广"))
            .phonoSemantic(TI_SHOU_PANG, zi("广"));

    public static final List<ChineseCharacterEntry> ALL = List.of(扩_EXPAND_ENLARGE);
}
