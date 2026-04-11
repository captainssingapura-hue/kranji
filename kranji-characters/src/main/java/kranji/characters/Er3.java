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

/** Characters pronounced er (tone 3). */
public final class Er3 {
    private Er3() {}

    /** 耳 (er3) — ear. */
    public static final ChineseCharacterEntry 耳_EAR = entry("耳")
            .py(ZERO, OPEN, Body.ER, Tail.NONE, T3).strokes(6).radical(128)
            .singular()
            .pictograph();

    public static final List<ChineseCharacterEntry> ALL = List.of(耳_EAR);
}
