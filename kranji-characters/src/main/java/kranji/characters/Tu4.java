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

/** Characters pronounced tu (tone 4). */
public final class Tu4 {
    private Tu4() {}

    /** 兔 (tu4) — rabbit. */
    public static final ChineseCharacterEntry 兔_RABBIT = entry("兔")
            .py(T, U, Body.U, Tail.NONE, T4).strokes(8).radical(10)
            .singular()
            .pictograph();

    public static final List<ChineseCharacterEntry> ALL = List.of(兔_RABBIT);
}
