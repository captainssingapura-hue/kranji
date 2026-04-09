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

/** Characters pronounced jin (tone 3). */
public final class Jin3 {
    private Jin3() {}

    /** 仅 (jin3) — only; merely. */
    public static final ChineseCharacterEntry 仅_ONLY_MERELY = entry("仅")
            .py(J, OPEN, Body.I, Tail.N, T3).strokes(4).radical(9)
            .leftRight(DAN_REN_PANG, zi("又"))
            .phonoSemantic(DAN_REN_PANG, zi("又"));

    /** 紧 (jin3) — tight; urgent. */
    public static final ChineseCharacterEntry 紧_TIGHT_URGENT = entry("紧")
            .py(J, OPEN, Body.I, Tail.N, T3).strokes(10).radical(120)
            .topBottom(zi("臤"), zi("糸"))
            .phonoSemantic(zi("糸"), zi("臤"));

    public static final List<ChineseCharacterEntry> ALL = List.of(仅_ONLY_MERELY, 紧_TIGHT_URGENT);
}
