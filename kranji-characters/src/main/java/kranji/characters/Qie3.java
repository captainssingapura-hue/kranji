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

/** Characters pronounced qie (tone 3). */
public final class Qie3 {
    private Qie3() {}

    /** 且 (qie3) — and; moreover. */
    public static final ChineseCharacterEntry 且_AND_MOREOVER = entry("且")
            .py(Q, I, Body.E_CARON, Tail.NONE, T3).strokes(5).radical(1)
            .singular()
            .pictograph();

    public static final List<ChineseCharacterEntry> ALL = List.of(且_AND_MOREOVER);
}
