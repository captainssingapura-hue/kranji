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

/** Characters pronounced gong (tone 4). */
public final class Gong4 {
    private Gong4() {}

    /** 共 (gong4) — together; common. */
    public static final ChineseCharacterEntry 共_TOGETHER_COMMON = entry("共")
            .py(G, OPEN, Body.O, Tail.NG, T4).strokes(6).radical(12)
            .topBottom(zi("廾"), zi("一"))
            .compoundIndicative("together; common");

    public static final List<ChineseCharacterEntry> ALL = List.of(共_TOGETHER_COMMON);
}
