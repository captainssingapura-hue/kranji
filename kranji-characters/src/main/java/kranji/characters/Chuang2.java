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

/** Characters pronounced chuang (tone 2). */
public final class Chuang2 {
    private Chuang2() {}

    /** 床 (chuang2) — bed. */
    public static final ChineseCharacterEntry 床_BED = entry("床")
            .py(CH, U, Body.A, Tail.NG, T2).strokes(7).radical(53)
            .semiEnclosureUL(zi("广"), zi("木"))
            .phonoSemantic(zi("广"), zi("木"));

    public static final List<ChineseCharacterEntry> ALL = List.of(床_BED);
}
