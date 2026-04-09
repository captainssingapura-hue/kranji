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

/** Characters pronounced zhuo (tone 1). */
public final class Zhuo1 {
    private Zhuo1() {}

    /** 桌 (zhuo1) — table; desk. */
    public static final ChineseCharacterEntry 桌_TABLE_DESK = entry("桌")
            .py(ZH, U, Body.O, Tail.NONE, T1).strokes(10).radical(75)
            .topBottom(zi("卜日"), zi("木"))
            .compoundIndicative("table; desk");

    public static final List<ChineseCharacterEntry> ALL = List.of(桌_TABLE_DESK);
}
