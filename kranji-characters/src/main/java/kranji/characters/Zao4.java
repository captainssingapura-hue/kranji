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

/** Characters pronounced zao (tone 4). */
public final class Zao4 {
    private Zao4() {}

    /** 造 (zao4) — create; make. */
    public static final ChineseCharacterEntry 造_CREATE_MAKE = entry("造")
            .py(Z, OPEN, Body.A, Tail.VOWEL_U, T4).strokes(10).radical(162)
            .semiEnclosureBL(ZOU_ZHI_DI, zi("告"))
            .phonoSemantic(ZOU_ZHI_DI, zi("告"));

    public static final List<ChineseCharacterEntry> ALL = List.of(造_CREATE_MAKE);
}
