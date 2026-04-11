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

/** Characters pronounced san (tone 3). */
public final class San3 {
    private San3() {}

    /** 伞 (san3) — umbrella. */
    public static final ChineseCharacterEntry 伞_UMBRELLA = entry("伞")
            .py(S, OPEN, Body.A, Tail.N, T3).strokes(6).radical(9)
            .topBottom(zi("人"), zi("十十"))
            .compoundIndicative("umbrella");

    public static final List<ChineseCharacterEntry> ALL = List.of(伞_UMBRELLA);
}
