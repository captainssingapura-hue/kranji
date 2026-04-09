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

/** Characters pronounced bie (tone 2). */
public final class Bie2 {
    private Bie2() {}

    /** 别 (bie2) — other; don't. */
    public static final ChineseCharacterEntry 别_OTHER_DON_T = entry("别")
            .py(B, I, Body.E_CARON, Tail.NONE, T2).strokes(7).radical(18)
            .leftRight(zi("另"), LI_DAO_PANG)
            .phonoSemantic(zi("另"), LI_DAO_PANG);

    public static final List<ChineseCharacterEntry> ALL = List.of(别_OTHER_DON_T);
}
