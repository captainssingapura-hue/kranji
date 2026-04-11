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

/** Characters pronounced ban (tone 3). */
public final class Ban3 {
    private Ban3() {}

    /** 版 (ban3) — edition; plate. */
    public static final ChineseCharacterEntry 版_EDITION_PLATE = entry("版")
            .py(B, OPEN, Body.A, Tail.N, T3).strokes(8).radical(91)
            .leftRight(zi("片"), zi("反"))
            .phonoSemantic(zi("片"), zi("反"));

    public static final List<ChineseCharacterEntry> ALL = List.of(版_EDITION_PLATE);
}
