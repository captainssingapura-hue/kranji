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

/** Characters pronounced guo (tone 4). */
public final class Guo4 {
    private Guo4() {}

    /** 过 (guo4) — pass; cross. */
    public static final ChineseCharacterEntry 过_PASS_CROSS = entry("过")
            .py(G, U, Body.O, Tail.NONE, T4).strokes(6).radical(162)
            .semiEnclosureBL(ZOU_ZHI_DI, zi("寸"))
            .phonoSemantic(ZOU_ZHI_DI, zi("寸"));

    public static final List<ChineseCharacterEntry> ALL = List.of(过_PASS_CROSS);
}
