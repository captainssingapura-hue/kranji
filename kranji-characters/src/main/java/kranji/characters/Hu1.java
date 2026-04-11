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

/** Characters pronounced hu (tone 1). */
public final class Hu1 {
    private Hu1() {}

    /** 忽 (hu1) — neglect; sudden. */
    public static final ChineseCharacterEntry 忽_NEGLECT_SUDDEN = entry("忽")
            .py(H, U, Body.U, Tail.NONE, T1).strokes(8).radical(61)
            .topBottom(zi("勿"), zi("心"))
            .phonoSemantic(zi("心"), zi("勿"));

    /** 呼 (hu1) — call; breathe. */
    public static final ChineseCharacterEntry 呼_CALL_BREATHE = entry("呼")
            .py(H, U, Body.U, Tail.NONE, T1).strokes(8).radical(30)
            .leftRight(zi("口"), zi("乎"))
            .phonoSemantic(zi("口"), zi("乎"));

    public static final List<ChineseCharacterEntry> ALL = List.of(忽_NEGLECT_SUDDEN, 呼_CALL_BREATHE);
}
