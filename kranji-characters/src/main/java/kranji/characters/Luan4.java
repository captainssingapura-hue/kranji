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

/** Characters pronounced luan (tone 4). */
public final class Luan4 {
    private Luan4() {}

    /** 乱 (luan4) — chaotic; mess. */
    public static final ChineseCharacterEntry 乱_CHAOTIC_MESS = entry("乱")
            .py(L, U, Body.A, Tail.N, T4).strokes(7).radical(5)
            .leftRight(zi("舌"), zi("乚"))
            .compoundIndicative("chaotic; mess");

    public static final List<ChineseCharacterEntry> ALL = List.of(乱_CHAOTIC_MESS);
}
