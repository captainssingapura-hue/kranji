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

/** Characters pronounced xia (tone 4). */
public final class Xia4 {
    private Xia4() {}

    /** 下 (xia) — below. Singular indicative. */
    public static final ChineseCharacterEntry 下_BELOW = entry("下")
            .py(X, I, Body.A, Tail.NONE, T4).strokes(3).radical(1)
            .singular()
            .indicative("stroke below the baseline marks 'below'");

    public static final List<ChineseCharacterEntry> ALL = List.of(下_BELOW);
}
