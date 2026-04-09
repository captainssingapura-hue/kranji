package kranji.characters;

import kranji.entry.ChineseCharacterEntry;

import java.util.List;

import static kranji.characters.Comp.*;
import static kranji.characters.EntryBuilder.entry;
import static kranji.component.Parts.*;
import static kranji.pinyin.Initial.*;
import static kranji.pinyin.Head.*;
import kranji.pinyin.Body;
import kranji.pinyin.Tail;

/** Characters pronounced hong (tone 2). */
public final class Hong2 {
    private Hong2() {}

    /** 红 (hong2) — red. */
    public static final ChineseCharacterEntry 红_RED = entry("红")
            .py(H, OPEN, Body.O, Tail.NG, T2).strokes(6).radical(120)
            .leftRight(JIAO_SI_PANG, zi("工"))
            .phonoSemantic(JIAO_SI_PANG, zi("工"));

    public static final List<ChineseCharacterEntry> ALL = List.of(红_RED);
}
