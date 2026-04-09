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

/** Characters pronounced lian (tone 2). */
public final class Lian2 {
    private Lian2() {}

    /** 连 (lian2) — connect; even. */
    public static final ChineseCharacterEntry 连_CONNECT_EVEN = entry("连")
            .py(L, I, Body.A, Tail.N, T2).strokes(7).radical(162)
            .semiEnclosureBL(ZOU_ZHI_DI, zi("车"))
            .phonoSemantic(ZOU_ZHI_DI, zi("车"));

    /** 联 (lian2) — unite; connect. */
    public static final ChineseCharacterEntry 联_UNITE_CONNECT = entry("联")
            .py(L, I, Body.A, Tail.N, T2).strokes(12).radical(128)
            .leftRight(zi("耳"), zi("关"))
            .phonoSemantic(zi("耳"), zi("关"));

    /** 莲 (lian2) — lotus. */
    public static final ChineseCharacterEntry 莲_LOTUS = entry("莲")
            .py(L, I, Body.A, Tail.N, T2).strokes(10).radical(140)
            .topBottom(CAO_ZI_TOU, zi("连"))
            .phonoSemantic(CAO_ZI_TOU, zi("连"));

    public static final List<ChineseCharacterEntry> ALL = List.of(连_CONNECT_EVEN, 联_UNITE_CONNECT, 莲_LOTUS);
}
