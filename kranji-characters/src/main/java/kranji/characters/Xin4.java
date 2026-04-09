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

/** Characters pronounced xin (tone 4). */
public final class Xin4 {
    private Xin4() {}

    /** 信 (xin4) — believe; letter. */
    public static final ChineseCharacterEntry 信_BELIEVE_LETTER = entry("信")
            .py(X, OPEN, Body.I, Tail.N, T4).strokes(9).radical(9)
            .leftRight(DAN_REN_PANG, zi("言"))
            .compoundIndicative("believe; letter");

    public static final List<ChineseCharacterEntry> ALL = List.of(信_BELIEVE_LETTER);
}
