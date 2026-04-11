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

/** Characters pronounced kong (tone 3). */
public final class Kong3 {
    private Kong3() {}

    /** 孔 (kong) — hole. LeftRight: 子 + 乚. Compound indicative. */
    public static final ChineseCharacterEntry 孔_HOLE = entry("孔")
            .py(K, OPEN, Body.O, Tail.NG, T3).strokes(4).radical(39)
            .leftRight(zi("子"), zi("乚"))
            .compoundIndicative("子(child) + 乚(opening) → hole");

    public static final List<ChineseCharacterEntry> ALL = List.of(孔_HOLE);
}
