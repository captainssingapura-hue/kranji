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

/** Characters pronounced gei (tone 3). */
public final class Gei3 {
    private Gei3() {}

    /** 给 (gei3) — give. */
    public static final ChineseCharacterEntry 给_GIVE = entry("给")
            .py(G, OPEN, Body.E, Tail.VOWEL_I, T3).strokes(9).radical(120)
            .leftRight(JIAO_SI_PANG, zi("合"))
            .phonoSemantic(JIAO_SI_PANG, zi("合"));

    public static final List<ChineseCharacterEntry> ALL = List.of(给_GIVE);
}
