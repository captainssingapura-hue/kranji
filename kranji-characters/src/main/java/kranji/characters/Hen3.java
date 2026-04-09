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

/** Characters pronounced hen (tone 3). */
public final class Hen3 {
    private Hen3() {}

    /** 很 (hen3) — very. */
    public static final ChineseCharacterEntry 很_VERY = entry("很")
            .py(H, OPEN, Body.E, Tail.N, T3).strokes(9).radical(60)
            .leftRight(SHUANG_REN_PANG, zi("艮"))
            .phonoSemantic(SHUANG_REN_PANG, zi("艮"));

    public static final List<ChineseCharacterEntry> ALL = List.of(很_VERY);
}
