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

/** Characters pronounced wen (tone 3). */
public final class Wen3 {
    private Wen3() {}

    /** 吻 (wen3) — kiss; lips. */
    public static final ChineseCharacterEntry 吻_KISS_LIPS = entry("吻")
            .py(ZERO, U, Body.E, Tail.N, T3).strokes(7).radical(30)
            .leftRight(zi("口"), zi("勿"))
            .phonoSemantic(zi("口"), zi("勿"));

    public static final List<ChineseCharacterEntry> ALL = List.of(吻_KISS_LIPS);
}
