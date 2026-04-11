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

/** Characters pronounced xi (tone 4). */
public final class Xi4 {
    private Xi4() {}

    /** 系 (xi4) — system; tie. */
    public static final ChineseCharacterEntry 系_SYSTEM_TIE = entry("系")
            .py(X, OPEN, Body.I, Tail.NONE, T4).strokes(7).radical(120)
            .singular()
            .pictograph();

    /** 细 (xi4) — thin; fine. */
    public static final ChineseCharacterEntry 细_THIN_FINE = entry("细")
            .py(X, OPEN, Body.I, Tail.NONE, T4).strokes(8).radical(120)
            .leftRight(JIAO_SI_PANG, zi("田"))
            .phonoSemantic(JIAO_SI_PANG, zi("田"));

    public static final List<ChineseCharacterEntry> ALL = List.of(系_SYSTEM_TIE, 细_THIN_FINE);
}
