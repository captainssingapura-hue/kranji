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

/** Characters pronounced tie (tone 1). */
public final class Tie1 {
    private Tie1() {}

    /** 贴 (tie1) — paste; close. */
    public static final ChineseCharacterEntry 贴_PASTE_CLOSE = entry("贴")
            .py(T, I, Body.E_CARON, Tail.NONE, T1).strokes(9).radical(154)
            .leftRight(zi("贝"), zi("占"))
            .phonoSemantic(zi("贝"), zi("占"));

    public static final List<ChineseCharacterEntry> ALL = List.of(贴_PASTE_CLOSE);
}
