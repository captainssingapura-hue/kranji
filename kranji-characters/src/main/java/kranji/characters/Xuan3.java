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

/** Characters pronounced xuan (tone 3). */
public final class Xuan3 {
    private Xuan3() {}

    /** 选 (xuan3) — select; elect. */
    public static final ChineseCharacterEntry 选_SELECT_ELECT = entry("选")
            .py(X, V, Body.A, Tail.N, T3).strokes(9).radical(162)
            .semiEnclosureBL(ZOU_ZHI_DI, zi("先"))
            .phonoSemantic(ZOU_ZHI_DI, zi("先"));

    public static final List<ChineseCharacterEntry> ALL = List.of(选_SELECT_ELECT);
}
