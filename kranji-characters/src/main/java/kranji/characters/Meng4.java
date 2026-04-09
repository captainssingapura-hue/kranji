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

/** Characters pronounced meng (tone 4). */
public final class Meng4 {
    private Meng4() {}

    /** 梦 (meng4) — dream. */
    public static final ChineseCharacterEntry 梦_DREAM = entry("梦")
            .py(M, OPEN, Body.E, Tail.NG, T4).strokes(11).radical(75)
            .topBottom(zi("林"), zi("夕"))
            .compoundIndicative("dream");

    public static final List<ChineseCharacterEntry> ALL = List.of(梦_DREAM);
}
