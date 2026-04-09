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

/** Characters pronounced yi (tone 2). */
public final class Yi2 {
    private Yi2() {}

    /** 疑 (yi2) — doubt; suspect. */
    public static final ChineseCharacterEntry 疑_DOUBT_SUSPECT = entry("疑")
            .py(ZERO, OPEN, Body.I, Tail.NONE, T2).strokes(14).radical(103)
            .singular()
            .compoundIndicative("doubt; suspect");

    /** 遗 (yi2) — leave behind. */
    public static final ChineseCharacterEntry 遗_LEAVE_BEHIND = entry("遗")
            .py(ZERO, OPEN, Body.I, Tail.NONE, T2).strokes(12).radical(162)
            .semiEnclosureBL(ZOU_ZHI_DI, zi("贵"))
            .phonoSemantic(ZOU_ZHI_DI, zi("贵"));

    /** 移 (yi2) — move; shift. */
    public static final ChineseCharacterEntry 移_MOVE_SHIFT = entry("移")
            .py(ZERO, OPEN, Body.I, Tail.NONE, T2).strokes(11).radical(115)
            .leftRight(zi("禾"), zi("多"))
            .phonoSemantic(zi("禾"), zi("多"));

    public static final List<ChineseCharacterEntry> ALL = List.of(疑_DOUBT_SUSPECT, 遗_LEAVE_BEHIND, 移_MOVE_SHIFT);
}
