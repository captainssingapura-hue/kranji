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

/** Characters pronounced wei (tone 2). */
public final class Wei2 {
    private Wei2() {}

    /** 维 (wei2) — maintain; fiber. */
    public static final ChineseCharacterEntry 维_MAINTAIN_FIBER = entry("维")
            .py(ZERO, U, Body.E, Tail.VOWEL_I, T2).strokes(11).radical(120)
            .leftRight(JIAO_SI_PANG, zi("隹"))
            .phonoSemantic(JIAO_SI_PANG, zi("隹"));

    /** 围 (wei2) — surround. */
    public static final ChineseCharacterEntry 围_SURROUND = entry("围")
            .py(ZERO, U, Body.E, Tail.VOWEL_I, T2).strokes(7).radical(31)
            .fullEnclosure(GUO_ZI_KUANG, zi("韦"))
            .phonoSemantic(GUO_ZI_KUANG, zi("韦"));

    public static final List<ChineseCharacterEntry> ALL = List.of(维_MAINTAIN_FIBER, 围_SURROUND);
}
