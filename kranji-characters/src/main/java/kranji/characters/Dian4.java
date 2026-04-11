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

/** Characters pronounced dian (tone 4). */
public final class Dian4 {
    private Dian4() {}

    /** 电 (dian4) — electric. */
    public static final ChineseCharacterEntry 电_ELECTRIC = entry("电")
            .py(D, I, Body.A, Tail.N, T4).strokes(5).radical(173)
            .singular()
            .pictograph();

    public static final List<ChineseCharacterEntry> ALL = List.of(电_ELECTRIC);
}
