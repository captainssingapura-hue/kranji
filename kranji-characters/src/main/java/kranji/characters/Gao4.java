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

/** Characters pronounced gao (tone 4). */
public final class Gao4 {
    private Gao4() {}

    /** 告 (gao4) — tell; sue. */
    public static final ChineseCharacterEntry 告_TELL_SUE = entry("告")
            .py(G, OPEN, Body.A, Tail.VOWEL_U, T4).strokes(7).radical(30)
            .topBottom(zi("牛"), zi("口"))
            .compoundIndicative("tell; sue");

    public static final List<ChineseCharacterEntry> ALL = List.of(告_TELL_SUE);
}
