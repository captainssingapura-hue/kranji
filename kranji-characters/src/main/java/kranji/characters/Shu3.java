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

/** Characters pronounced shu (tone 3). */
public final class Shu3 {
    private Shu3() {}

    /** 属 (shu3) — belong; genus. */
    public static final ChineseCharacterEntry 属_BELONG_GENUS = entry("属")
            .py(SH, U, Body.U, Tail.NONE, T3).strokes(12).radical(44)
            .semiEnclosureUL(zi("尸"), zi("禹"))
            .phonoSemantic(zi("尸"), zi("禹"));

    /** 鼠 (shu3) — mouse; rat. */
    public static final ChineseCharacterEntry 鼠_MOUSE_RAT = entry("鼠")
            .py(SH, U, Body.U, Tail.NONE, T3).strokes(13).radical(208)
            .singular()
            .pictograph();

    public static final List<ChineseCharacterEntry> ALL = List.of(属_BELONG_GENUS, 鼠_MOUSE_RAT);
}
