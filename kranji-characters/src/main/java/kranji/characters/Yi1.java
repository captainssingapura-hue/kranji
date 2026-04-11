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

/** Characters pronounced yi (tone 1). */
public final class Yi1 {
    private Yi1() {}

    /** 一 (yi1) — one. */
    public static final ChineseCharacterEntry 一_ONE = entry("一")
            .py(ZERO, OPEN, Body.I, Tail.NONE, T1).strokes(1).radical(1)
            .singular()
            .indicative("one");

    /** 依 (yi1) — depend; according. */
    public static final ChineseCharacterEntry 依_DEPEND_ACCORDING = entry("依")
            .py(ZERO, OPEN, Body.I, Tail.NONE, T1).strokes(8).radical(9)
            .leftRight(DAN_REN_PANG, zi("衣"))
            .phonoSemantic(DAN_REN_PANG, zi("衣"));

    public static final List<ChineseCharacterEntry> ALL = List.of(一_ONE, 依_DEPEND_ACCORDING);
}
