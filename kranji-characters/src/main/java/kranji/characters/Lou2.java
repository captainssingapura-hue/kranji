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

/** Characters pronounced lou (tone 2). */
public final class Lou2 {
    private Lou2() {}

    /** 楼 (lou2) — floor; building. */
    public static final ChineseCharacterEntry 楼_FLOOR_BUILDING = entry("楼")
            .py(L, OPEN, Body.O, Tail.VOWEL_U, T2).strokes(13).radical(75)
            .leftRight(zi("木"), zi("娄"))
            .phonoSemantic(zi("木"), zi("娄"));

    public static final List<ChineseCharacterEntry> ALL = List.of(楼_FLOOR_BUILDING);
}
