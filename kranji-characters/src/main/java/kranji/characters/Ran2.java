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

/** Characters pronounced ran (tone 2). */
public final class Ran2 {
    private Ran2() {}

    /** 然 (ran2) — so; like that. */
    public static final ChineseCharacterEntry 然_SO_LIKE_THAT = entry("然")
            .py(R, OPEN, Body.A, Tail.N, T2).strokes(12).radical(86)
            .topBottom(zi("肰"), SI_DIAN_DI)
            .phonoSemantic(SI_DIAN_DI, zi("肰"));

    public static final List<ChineseCharacterEntry> ALL = List.of(然_SO_LIKE_THAT);
}
