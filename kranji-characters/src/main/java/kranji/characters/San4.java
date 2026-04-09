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

/** Characters pronounced san (tone 4). */
public final class San4 {
    private San4() {}

    /** 散 (san4) — scatter. */
    public static final ChineseCharacterEntry 散_SCATTER = entry("散")
            .py(S, OPEN, Body.A, Tail.N, T4).strokes(12).radical(66)
            .leftRight(zi("散"), FAN_WEN_PANG)
            .compoundIndicative("scatter");

    public static final List<ChineseCharacterEntry> ALL = List.of(散_SCATTER);
}
