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

/** Characters pronounced zhang (tone 4). */
public final class Zhang4 {
    private Zhang4() {}

    /** 丈 (zhang4) — measure; elder. */
    public static final ChineseCharacterEntry 丈_MEASURE_ELDER = entry("丈")
            .py(ZH, OPEN, Body.A, Tail.NG, T4).strokes(3).radical(1)
            .singular()
            .indicative("measure; elder");

    public static final List<ChineseCharacterEntry> ALL = List.of(丈_MEASURE_ELDER);
}
