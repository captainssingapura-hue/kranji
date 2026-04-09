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

/** Characters pronounced qin (tone 1). */
public final class Qin1 {
    private Qin1() {}

    /** 亲 (qin1) — parent; close. */
    public static final ChineseCharacterEntry 亲_PARENT_CLOSE = entry("亲")
            .py(Q, OPEN, Body.I, Tail.N, T1).strokes(9).radical(8)
            .topBottom(zi("立"), zi("木"))
            .compoundIndicative("parent; close");

    public static final List<ChineseCharacterEntry> ALL = List.of(亲_PARENT_CLOSE);
}
