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

/** Characters pronounced huan (tone 4). */
public final class Huan4 {
    private Huan4() {}

    /** 换 (huan4) — exchange. */
    public static final ChineseCharacterEntry 换_EXCHANGE = entry("换")
            .py(H, U, Body.A, Tail.N, T4).strokes(10).radical(64)
            .leftRight(TI_SHOU_PANG, zi("奂"))
            .phonoSemantic(TI_SHOU_PANG, zi("奂"));

    /** 唤 (huan4) — call; summon. */
    public static final ChineseCharacterEntry 唤_CALL_SUMMON = entry("唤")
            .py(H, U, Body.A, Tail.N, T4).strokes(10).radical(30)
            .leftRight(zi("口"), zi("奂"))
            .phonoSemantic(zi("口"), zi("奂"));

    public static final List<ChineseCharacterEntry> ALL = List.of(换_EXCHANGE, 唤_CALL_SUMMON);
}
