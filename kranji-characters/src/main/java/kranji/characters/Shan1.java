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

/** Characters pronounced shan (tone 1). */
public final class Shan1 {
    private Shan1() {}

    /** 山 (shan) — mountain. Singular pictograph. */
    public static final ChineseCharacterEntry 山_MOUNTAIN = entry("山")
            .py(SH, OPEN, Body.A, Tail.N, T1).strokes(3).radical(46)
            .singular()
            .pictograph();

    public static final List<ChineseCharacterEntry> ALL = List.of(山_MOUNTAIN);
}
