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

/** Characters pronounced zhe (tone 3). */
public final class Zhe3 {
    private Zhe3() {}

    /** 者 (zhe3) — person who. */
    public static final ChineseCharacterEntry 者_PERSON_WHO = entry("者")
            .py(ZH, OPEN, Body.E, Tail.NONE, T3).strokes(8).radical(125)
            .topBottom(zi("耂"), zi("日"))
            .compoundIndicative("person who");

    public static final List<ChineseCharacterEntry> ALL = List.of(者_PERSON_WHO);
}
