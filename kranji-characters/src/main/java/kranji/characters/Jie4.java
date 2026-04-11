package kranji.characters;

import kranji.entry.ChineseCharacterEntry;

import java.util.List;

import static kranji.characters.Comp.*;
import static kranji.characters.EntryBuilder.entry;
import static kranji.component.basic.BasicComponents.*;
import static kranji.pinyin.Initial.*;
import static kranji.pinyin.Head.*;
import kranji.pinyin.Body;
import kranji.pinyin.Tail;

/** Characters pronounced jie (tone 4). */
public final class Jie4 {
    private Jie4() {}

    /** 界 (jie4) — boundary. */
    public static final ChineseCharacterEntry 界_BOUNDARY = entry("界")
            .py(J, I, Body.E_CARON, Tail.NONE, T4).strokes(9).radical(102)
            .topBottom(zi("田"), zi("介"))
            .phonoSemantic(zi("田"), zi("介"));

    /** 借 (jie4) — borrow; lend. */
    public static final ChineseCharacterEntry 借_BORROW_LEND = entry("借")
            .py(J, I, Body.E_CARON, Tail.NONE, T4).strokes(10).radical(9)
            .leftRight(DAN_REN_PANG, zi("昔"))
            .phonoSemantic(DAN_REN_PANG, zi("昔"));

    public static final List<ChineseCharacterEntry> ALL = List.of(界_BOUNDARY, 借_BORROW_LEND);
}
