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

/** Characters pronounced ren (tone 3). */
public final class Ren3 {
    private Ren3() {}

    /** 忍 (ren3) — endure; bear. */
    public static final ChineseCharacterEntry 忍_ENDURE_BEAR = entry("忍")
            .py(R, OPEN, Body.E, Tail.N, T3).strokes(7).radical(61)
            .topBottom(zi("刃"), zi("心"))
            .phonoSemantic(zi("心"), zi("刃"));

    public static final List<ChineseCharacterEntry> ALL = List.of(忍_ENDURE_BEAR);
}
