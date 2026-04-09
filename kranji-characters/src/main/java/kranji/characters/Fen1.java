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

/** Characters pronounced fen (tone 1). */
public final class Fen1 {
    private Fen1() {}

    /** 分 (fen1) — divide; part. */
    public static final ChineseCharacterEntry 分_DIVIDE_PART = entry("分")
            .py(F, OPEN, Body.E, Tail.N, T1).strokes(4).radical(18)
            .topBottom(zi("八"), zi("刀"))
            .compoundIndicative("divide; part");

    public static final List<ChineseCharacterEntry> ALL = List.of(分_DIVIDE_PART);
}
