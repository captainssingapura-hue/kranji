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

/** Characters pronounced cu (tone 4). */
public final class Cu4 {
    private Cu4() {}

    /** 醋 (cu4) — vinegar. */
    public static final ChineseCharacterEntry 醋_VINEGAR = entry("醋")
            .py(C, U, Body.U, Tail.NONE, T4).strokes(15).radical(164)
            .leftRight(zi("酉"), zi("昔"))
            .phonoSemantic(zi("酉"), zi("昔"));

    /** 促 (cu4) — urge; promote. */
    public static final ChineseCharacterEntry 促_URGE_PROMOTE = entry("促")
            .py(C, U, Body.U, Tail.NONE, T4).strokes(9).radical(9)
            .leftRight(DAN_REN_PANG, zi("足"))
            .phonoSemantic(DAN_REN_PANG, zi("足"));

    public static final List<ChineseCharacterEntry> ALL = List.of(醋_VINEGAR, 促_URGE_PROMOTE);
}
