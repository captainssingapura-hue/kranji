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

/** Characters pronounced ju (tone 2). */
public final class Ju2 {
    private Ju2() {}

    /** 局 (ju2) — bureau; game. */
    public static final ChineseCharacterEntry 局_BUREAU_GAME = entry("局")
            .py(J, V, Body.V, Tail.NONE, T2).strokes(7).radical(44)
            .semiEnclosureUL(zi("尸"), zi("句"))
            .phonoSemantic(zi("尸"), zi("句"));

    public static final List<ChineseCharacterEntry> ALL = List.of(局_BUREAU_GAME);
}
