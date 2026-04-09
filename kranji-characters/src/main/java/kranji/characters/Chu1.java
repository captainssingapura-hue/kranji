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

/** Characters pronounced chu (tone 1). */
public final class Chu1 {
    private Chu1() {}

    /** 出 (chu1) — go out. */
    public static final ChineseCharacterEntry 出_GO_OUT = entry("出")
            .py(CH, U, Body.U, Tail.NONE, T1).strokes(5).radical(17)
            .topBottom(zi("屮"), XIONG_ZI_KUANG)
            .compoundIndicative("go out");

    /** 初 (chu1) — first; initial. */
    public static final ChineseCharacterEntry 初_FIRST_INITIAL = entry("初")
            .py(CH, U, Body.U, Tail.NONE, T1).strokes(7).radical(18)
            .leftRight(YI_ZI_PANG, zi("刀"))
            .compoundIndicative("first; initial");

    public static final List<ChineseCharacterEntry> ALL = List.of(出_GO_OUT, 初_FIRST_INITIAL);
}
