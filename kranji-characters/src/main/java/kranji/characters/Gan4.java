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

/** Characters pronounced gan (tone 4). */
public final class Gan4 {
    private Gan4() {}

    /** 干 (gan4) — dry; do. */
    public static final ChineseCharacterEntry 干_DRY_DO = entry("干")
            .py(G, OPEN, Body.A, Tail.N, T4).strokes(3).radical(51)
            .singular()
            .pictograph();

    public static final List<ChineseCharacterEntry> ALL = List.of(干_DRY_DO);
}
