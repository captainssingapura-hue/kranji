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

/** Characters pronounced gen (tone 1). */
public final class Gen1 {
    private Gen1() {}

    /** 根 (gen1) — root; basis. */
    public static final ChineseCharacterEntry 根_ROOT_BASIS = entry("根")
            .py(G, OPEN, Body.E, Tail.N, T1).strokes(10).radical(75)
            .leftRight(zi("木"), zi("艮"))
            .phonoSemantic(zi("木"), zi("艮"));

    /** 跟 (gen1) — follow; with. */
    public static final ChineseCharacterEntry 跟_FOLLOW_WITH = entry("跟")
            .py(G, OPEN, Body.E, Tail.N, T1).strokes(13).radical(157)
            .leftRight(zi("足"), zi("艮"))
            .phonoSemantic(zi("足"), zi("艮"));

    public static final List<ChineseCharacterEntry> ALL = List.of(根_ROOT_BASIS, 跟_FOLLOW_WITH);
}
