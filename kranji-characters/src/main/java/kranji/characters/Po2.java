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

/** Characters pronounced po (tone 2). */
public final class Po2 {
    private Po2() {}

    /** 婆 (po2) — grandma; woman. */
    public static final ChineseCharacterEntry 婆_GRANDMA_WOMAN = entry("婆")
            .py(P, OPEN, Body.O, Tail.NONE, T2).strokes(11).radical(38)
            .topBottom(zi("波"), zi("女"))
            .phonoSemantic(zi("女"), zi("波"));

    public static final List<ChineseCharacterEntry> ALL = List.of(婆_GRANDMA_WOMAN);
}
