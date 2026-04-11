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

/** Characters pronounced pang (tone 4). */
public final class Pang4 {
    private Pang4() {}

    /** 胖 (pang4) — fat; plump. */
    public static final ChineseCharacterEntry 胖_FAT_PLUMP = entry("胖")
            .py(P, OPEN, Body.A, Tail.NG, T4).strokes(9).radical(130)
            .leftRight(zi("月"), zi("半"))
            .phonoSemantic(zi("月"), zi("半"));

    public static final List<ChineseCharacterEntry> ALL = List.of(胖_FAT_PLUMP);
}
