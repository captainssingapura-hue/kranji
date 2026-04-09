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

/** Characters pronounced ban (tone 1). */
public final class Ban1 {
    private Ban1() {}

    /** 般 (ban1) — sort; kind. */
    public static final ChineseCharacterEntry 般_SORT_KIND = entry("般")
            .py(B, OPEN, Body.A, Tail.N, T1).strokes(10).radical(137)
            .leftRight(zi("舟"), zi("殳"))
            .phonoSemantic(zi("舟"), zi("殳"));

    /** 搬 (ban1) — move; carry. */
    public static final ChineseCharacterEntry 搬_MOVE_CARRY = entry("搬")
            .py(B, OPEN, Body.A, Tail.N, T1).strokes(13).radical(64)
            .leftRight(TI_SHOU_PANG, zi("般"))
            .phonoSemantic(TI_SHOU_PANG, zi("般"));

    public static final List<ChineseCharacterEntry> ALL = List.of(般_SORT_KIND, 搬_MOVE_CARRY);
}
