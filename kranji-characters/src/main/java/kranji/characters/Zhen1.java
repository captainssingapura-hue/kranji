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

/** Characters pronounced zhen (tone 1). */
public final class Zhen1 {
    private Zhen1() {}

    /** 真 (zhen1) — true; real. */
    public static final ChineseCharacterEntry 真_TRUE_REAL = entry("真")
            .py(ZH, OPEN, Body.E, Tail.N, T1).strokes(10).radical(109)
            .topBottom(zi("直"), zi("八"))
            .phonoSemantic(zi("目"), zi("直"));

    public static final List<ChineseCharacterEntry> ALL = List.of(真_TRUE_REAL);
}
