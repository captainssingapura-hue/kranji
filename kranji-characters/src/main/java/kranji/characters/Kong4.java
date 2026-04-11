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

/** Characters pronounced kong (tone 4). */
public final class Kong4 {
    private Kong4() {}

    /** 控 (kong4) — control. */
    public static final ChineseCharacterEntry 控_CONTROL = entry("控")
            .py(K, OPEN, Body.O, Tail.NG, T4).strokes(11).radical(64)
            .leftRight(TI_SHOU_PANG, zi("空"))
            .phonoSemantic(TI_SHOU_PANG, zi("空"));

    public static final List<ChineseCharacterEntry> ALL = List.of(控_CONTROL);
}
