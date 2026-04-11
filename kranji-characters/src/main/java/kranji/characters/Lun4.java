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

/** Characters pronounced lun (tone 4). */
public final class Lun4 {
    private Lun4() {}

    /** 论 (lun4) — discuss; theory. */
    public static final ChineseCharacterEntry 论_DISCUSS_THEORY = entry("论")
            .py(L, U, Body.E, Tail.N, T4).strokes(6).radical(149)
            .leftRight(YAN_ZI_PANG, zi("仑"))
            .phonoSemantic(YAN_ZI_PANG, zi("仑"));

    public static final List<ChineseCharacterEntry> ALL = List.of(论_DISCUSS_THEORY);
}
