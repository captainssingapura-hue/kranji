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

/** Characters pronounced neng (tone 2). */
public final class Neng2 {
    private Neng2() {}

    /** 能 (neng2) — able; can. */
    public static final ChineseCharacterEntry 能_ABLE_CAN = entry("能")
            .py(N, OPEN, Body.E, Tail.NG, T2).strokes(10).radical(130)
            .leftRight(zi("厶"), zi("能"))
            .pictograph();

    public static final List<ChineseCharacterEntry> ALL = List.of(能_ABLE_CAN);
}
