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

/** Characters pronounced kang (tone 4). */
public final class Kang4 {
    private Kang4() {}

    /** 抗 (kang4) — resist; fight. */
    public static final ChineseCharacterEntry 抗_RESIST_FIGHT = entry("抗")
            .py(K, OPEN, Body.A, Tail.NG, T4).strokes(7).radical(64)
            .leftRight(TI_SHOU_PANG, zi("亢"))
            .phonoSemantic(TI_SHOU_PANG, zi("亢"));

    public static final List<ChineseCharacterEntry> ALL = List.of(抗_RESIST_FIGHT);
}
