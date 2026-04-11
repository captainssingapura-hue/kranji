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

/** Characters pronounced kuan (tone 1). */
public final class Kuan1 {
    private Kuan1() {}

    /** 宽 (kuan1) — wide; broad. */
    public static final ChineseCharacterEntry 宽_WIDE_BROAD = entry("宽")
            .py(K, U, Body.A, Tail.N, T1).strokes(10).radical(40)
            .topBottom(BAO_GAI_TOU, zi("苋"))
            .phonoSemantic(BAO_GAI_TOU, zi("苋"));

    public static final List<ChineseCharacterEntry> ALL = List.of(宽_WIDE_BROAD);
}
