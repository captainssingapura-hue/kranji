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

/** Characters pronounced guo (tone 2). */
public final class Guo2 {
    private Guo2() {}

    /** 国 (guo) — country. FullEnclosure: 囗 + 玉. Phono-semantic. */
    public static final ChineseCharacterEntry 国_COUNTRY = entry("国")
            .py(G, U, Body.O, Tail.NONE, T2).strokes(8).radical(31)
            .fullEnclosure(GUO_ZI_KUANG, zi("玉"))
            .phonoSemantic(GUO_ZI_KUANG, zi("玉"));

    public static final List<ChineseCharacterEntry> ALL = List.of(国_COUNTRY);
}
