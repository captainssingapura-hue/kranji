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

/** Characters pronounced miao (tone 2). */
public final class Miao2 {
    private Miao2() {}

    /** 描 (miao2) — depict; trace. */
    public static final ChineseCharacterEntry 描_DEPICT_TRACE = entry("描")
            .py(M, I, Body.A, Tail.VOWEL_U, T2).strokes(11).radical(64)
            .leftRight(TI_SHOU_PANG, zi("苗"))
            .phonoSemantic(TI_SHOU_PANG, zi("苗"));

    public static final List<ChineseCharacterEntry> ALL = List.of(描_DEPICT_TRACE);
}
