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

/** Characters pronounced biao (tone 3). */
public final class Biao3 {
    private Biao3() {}

    /** 表 (biao3) — surface; list. */
    public static final ChineseCharacterEntry 表_SURFACE_LIST = entry("表")
            .py(B, I, Body.A, Tail.VOWEL_U, T3).strokes(8).radical(145)
            .topBottom(zi("龶"), zi("衣"))
            .compoundIndicative("surface; list");

    public static final List<ChineseCharacterEntry> ALL = List.of(表_SURFACE_LIST);
}
