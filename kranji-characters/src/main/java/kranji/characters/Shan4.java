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

/** Characters pronounced shan (tone 4). */
public final class Shan4 {
    private Shan4() {}

    /** 善 (shan4) — good; kind. */
    public static final ChineseCharacterEntry 善_GOOD_KIND = entry("善")
            .py(SH, OPEN, Body.A, Tail.N, T4).strokes(12).radical(30)
            .topBottom(zi("羊"), zi("口口"))
            .compoundIndicative("good; kind");

    public static final List<ChineseCharacterEntry> ALL = List.of(善_GOOD_KIND);
}
