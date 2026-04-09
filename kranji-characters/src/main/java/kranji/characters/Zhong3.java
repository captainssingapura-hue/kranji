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

/** Characters pronounced zhong (tone 3). */
public final class Zhong3 {
    private Zhong3() {}

    /** 种 (zhong3) — kind; seed. */
    public static final ChineseCharacterEntry 种_KIND_SEED = entry("种")
            .py(ZH, OPEN, Body.O, Tail.NG, T3).strokes(9).radical(115)
            .leftRight(zi("禾"), zi("中"))
            .phonoSemantic(zi("禾"), zi("中"));

    public static final List<ChineseCharacterEntry> ALL = List.of(种_KIND_SEED);
}
