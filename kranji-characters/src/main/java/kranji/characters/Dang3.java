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

/** Characters pronounced dang (tone 3). */
public final class Dang3 {
    private Dang3() {}

    /** 党 (dang3) — party. */
    public static final ChineseCharacterEntry 党_PARTY = entry("党")
            .py(D, OPEN, Body.A, Tail.NG, T3).strokes(10).radical(10)
            .topBottom(zi("⺌"), zi("兄"))
            .compoundIndicative("party");

    public static final List<ChineseCharacterEntry> ALL = List.of(党_PARTY);
}
