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

/** Characters pronounced pao (tone 3). */
public final class Pao3 {
    private Pao3() {}

    /** 跑 (pao3) — run. */
    public static final ChineseCharacterEntry 跑_RUN = entry("跑")
            .py(P, OPEN, Body.A, Tail.VOWEL_U, T3).strokes(12).radical(157)
            .leftRight(zi("足"), zi("包"))
            .phonoSemantic(zi("足"), zi("包"));

    public static final List<ChineseCharacterEntry> ALL = List.of(跑_RUN);
}
