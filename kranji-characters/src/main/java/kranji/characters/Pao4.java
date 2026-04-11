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

/** Characters pronounced pao (tone 4). */
public final class Pao4 {
    private Pao4() {}

    /** 炮 (pao4) — cannon; gun. */
    public static final ChineseCharacterEntry 炮_CANNON_GUN = entry("炮")
            .py(P, OPEN, Body.A, Tail.VOWEL_U, T4).strokes(9).radical(86)
            .leftRight(zi("火"), zi("包"))
            .phonoSemantic(zi("火"), zi("包"));

    public static final List<ChineseCharacterEntry> ALL = List.of(炮_CANNON_GUN);
}
