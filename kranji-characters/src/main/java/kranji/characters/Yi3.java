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

/** Characters pronounced yi (tone 3). */
public final class Yi3 {
    private Yi3() {}

    /** 已 (yi) — already. Singular pictograph. */
    public static final ChineseCharacterEntry 已_ALREADY = entry("已")
            .py(ZERO, OPEN, Body.I, Tail.NONE, T3).strokes(3).radical(49)
            .singular()
            .pictograph();

    public static final List<ChineseCharacterEntry> ALL = List.of(已_ALREADY);
}
