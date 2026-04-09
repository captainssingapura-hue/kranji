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

/** Characters pronounced guo (tone 1). */
public final class Guo1 {
    private Guo1() {}

    /** 锅 (guo1) — pot; pan. */
    public static final ChineseCharacterEntry 锅_POT_PAN = entry("锅")
            .py(G, U, Body.O, Tail.NONE, T1).strokes(12).radical(167)
            .leftRight(JIN_ZI_PANG, zi("呙"))
            .phonoSemantic(JIN_ZI_PANG, zi("呙"));

    public static final List<ChineseCharacterEntry> ALL = List.of(锅_POT_PAN);
}
