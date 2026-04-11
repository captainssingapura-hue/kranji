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

/** Characters pronounced ju (tone 1). */
public final class Ju1 {
    private Ju1() {}

    /** 居 (ju1) — live; reside. */
    public static final ChineseCharacterEntry 居_LIVE_RESIDE = entry("居")
            .py(J, V, Body.V, Tail.NONE, T1).strokes(8).radical(44)
            .semiEnclosureUL(zi("尸"), zi("古"))
            .phonoSemantic(zi("尸"), zi("古"));

    public static final List<ChineseCharacterEntry> ALL = List.of(居_LIVE_RESIDE);
}
