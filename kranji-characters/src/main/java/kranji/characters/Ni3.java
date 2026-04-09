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

/** Characters pronounced ni (tone 3). */
public final class Ni3 {
    private Ni3() {}

    /** 你 (ni3) — you. */
    public static final ChineseCharacterEntry 你_YOU = entry("你")
            .py(N, OPEN, Body.I, Tail.NONE, T3).strokes(7).radical(9)
            .leftRight(DAN_REN_PANG, zi("尔"))
            .phonoSemantic(DAN_REN_PANG, zi("尔"));

    public static final List<ChineseCharacterEntry> ALL = List.of(你_YOU);
}
