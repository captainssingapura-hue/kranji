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

/** Characters pronounced mi (tone 3). */
public final class Mi3 {
    private Mi3() {}

    /** 米 (mi3) — rice. */
    public static final ChineseCharacterEntry 米_RICE = entry("米")
            .py(M, OPEN, Body.I, Tail.NONE, T3).strokes(6).radical(119)
            .singular()
            .pictograph();

    public static final List<ChineseCharacterEntry> ALL = List.of(米_RICE);
}
