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

/** Characters pronounced yin (tone 2). */
public final class Yin2 {
    private Yin2() {}

    /** 银 (yin2) — silver. */
    public static final ChineseCharacterEntry 银_SILVER = entry("银")
            .py(ZERO, OPEN, Body.I, Tail.N, T2).strokes(11).radical(167)
            .leftRight(JIN_ZI_PANG, zi("艮"))
            .phonoSemantic(JIN_ZI_PANG, zi("艮"));

    public static final List<ChineseCharacterEntry> ALL = List.of(银_SILVER);
}
