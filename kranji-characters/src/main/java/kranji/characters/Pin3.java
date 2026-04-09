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

/** Characters pronounced pin (tone 3). */
public final class Pin3 {
    private Pin3() {}

    /** 品 (pin) — product. Mosaic: 口 x 3. Compound indicative. */
    public static final ChineseCharacterEntry 品_PRODUCT = entry("品")
            .py(P, OPEN, Body.I, Tail.N, T3).strokes(9).radical(30)
            .mosaic(zi("口"))
            .compoundIndicative("口(mouth) × 3 → many mouths → tasting, judging quality");

    public static final List<ChineseCharacterEntry> ALL = List.of(品_PRODUCT);
}
