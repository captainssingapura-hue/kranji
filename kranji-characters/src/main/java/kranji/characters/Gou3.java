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

/** Characters pronounced gou (tone 3). */
public final class Gou3 {
    private Gou3() {}

    /** 狗 (gou3) — dog. */
    public static final ChineseCharacterEntry 狗_DOG = entry("狗")
            .py(G, OPEN, Body.O, Tail.VOWEL_U, T3).strokes(8).radical(94)
            .leftRight(FAN_QUAN_PANG, zi("句"))
            .phonoSemantic(FAN_QUAN_PANG, zi("句"));

    public static final List<ChineseCharacterEntry> ALL = List.of(狗_DOG);
}
