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

/** Characters pronounced kong (tone 1). */
public final class Kong1 {
    private Kong1() {}

    /** 空 (kong1) — empty; sky. */
    public static final ChineseCharacterEntry 空_EMPTY_SKY = entry("空")
            .py(K, OPEN, Body.O, Tail.NG, T1).strokes(8).radical(116)
            .topBottom(zi("穴"), zi("工"))
            .phonoSemantic(zi("穴"), zi("工"));

    public static final List<ChineseCharacterEntry> ALL = List.of(空_EMPTY_SKY);
}
