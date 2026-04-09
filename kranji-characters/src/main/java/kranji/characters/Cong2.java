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

/** Characters pronounced cong (tone 2). */
public final class Cong2 {
    private Cong2() {}

    /** 从 (cong2) — from; follow. */
    public static final ChineseCharacterEntry 从_FROM_FOLLOW = entry("从")
            .py(C, OPEN, Body.O, Tail.NG, T2).strokes(4).radical(9)
            .leftRight(zi("人"), zi("人"))
            .compoundIndicative("from; follow");

    public static final List<ChineseCharacterEntry> ALL = List.of(从_FROM_FOLLOW);
}
