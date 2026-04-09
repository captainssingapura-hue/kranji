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

/** Characters pronounced bian (tone 4). */
public final class Bian4 {
    private Bian4() {}

    /** 变 (bian4) — change. */
    public static final ChineseCharacterEntry 变_CHANGE = entry("变")
            .py(B, I, Body.A, Tail.N, T4).strokes(8).radical(29)
            .topBottom(zi("亦"), zi("又"))
            .compoundIndicative("change");

    /** 便 (bian4) — convenient. */
    public static final ChineseCharacterEntry 便_CONVENIENT = entry("便")
            .py(B, I, Body.A, Tail.N, T4).strokes(9).radical(9)
            .leftRight(DAN_REN_PANG, zi("更"))
            .phonoSemantic(DAN_REN_PANG, zi("更"));

    /** 遍 (bian4) — everywhere. */
    public static final ChineseCharacterEntry 遍_EVERYWHERE = entry("遍")
            .py(B, I, Body.A, Tail.N, T4).strokes(12).radical(162)
            .semiEnclosureBL(ZOU_ZHI_DI, zi("扁"))
            .phonoSemantic(ZOU_ZHI_DI, zi("扁"));

    public static final List<ChineseCharacterEntry> ALL = List.of(变_CHANGE, 便_CONVENIENT, 遍_EVERYWHERE);
}
