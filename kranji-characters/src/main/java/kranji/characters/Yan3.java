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

/** Characters pronounced yan (tone 3). */
public final class Yan3 {
    private Yan3() {}

    /** 眼 (yan3) — eye. */
    public static final ChineseCharacterEntry 眼_EYE = entry("眼")
            .py(ZERO, I, Body.A, Tail.N, T3).strokes(11).radical(109)
            .leftRight(zi("目"), zi("艮"))
            .phonoSemantic(zi("目"), zi("艮"));

    public static final List<ChineseCharacterEntry> ALL = List.of(眼_EYE);
}
