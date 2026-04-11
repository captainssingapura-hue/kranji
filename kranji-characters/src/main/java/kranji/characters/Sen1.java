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

/** Characters pronounced sen (tone 1). */
public final class Sen1 {
    private Sen1() {}

    /** 森 (sen) — dense forest. Mosaic: 木 x 3. Compound indicative. */
    public static final ChineseCharacterEntry 森_DENSE_FOREST = entry("森")
            .py(S, OPEN, Body.E, Tail.N, T1).strokes(12).radical(75)
            .mosaic(MU)
            .compoundIndicative("木(tree) × 3 → dense forest");

    public static final List<ChineseCharacterEntry> ALL = List.of(森_DENSE_FOREST);
}
