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

/** Characters pronounced te (tone 4). */
public final class Te4 {
    private Te4() {}

    /** 特 (te4) — special. */
    public static final ChineseCharacterEntry 特_SPECIAL = entry("特")
            .py(T, OPEN, Body.E, Tail.NONE, T4).strokes(10).radical(93)
            .leftRight(zi("牜"), zi("寺"))
            .phonoSemantic(zi("牜"), zi("寺"));

    public static final List<ChineseCharacterEntry> ALL = List.of(特_SPECIAL);
}
