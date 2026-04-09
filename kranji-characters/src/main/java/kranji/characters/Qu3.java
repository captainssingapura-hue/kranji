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

/** Characters pronounced qu (tone 3). */
public final class Qu3 {
    private Qu3() {}

    /** 取 (qu3) — take; get. */
    public static final ChineseCharacterEntry 取_TAKE_GET = entry("取")
            .py(Q, V, Body.V, Tail.NONE, T3).strokes(8).radical(29)
            .leftRight(zi("耳"), zi("又"))
            .compoundIndicative("take; get");

    public static final List<ChineseCharacterEntry> ALL = List.of(取_TAKE_GET);
}
