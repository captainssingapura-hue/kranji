package kranji.characters;

import kranji.entry.ChineseCharacterEntry;

import java.util.List;

import static kranji.characters.Comp.*;
import static kranji.characters.EntryBuilder.entry;
import static kranji.component.basic.BasicComponents.*;
import static kranji.pinyin.Initial.*;
import static kranji.pinyin.Head.*;
import kranji.pinyin.Body;
import kranji.pinyin.Tail;

/** Characters pronounced miao (tone 4). */
public final class Miao4 {
    private Miao4() {}

    /** 庙 (miao4) — temple. */
    public static final ChineseCharacterEntry 庙_TEMPLE = entry("庙")
            .py(M, I, Body.A, Tail.VOWEL_U, T4).strokes(8).radical(53)
            .semiEnclosureUL(zi("广"), zi("朝"))
            .phonoSemantic(zi("广"), zi("朝"));

    public static final List<ChineseCharacterEntry> ALL = List.of(庙_TEMPLE);
}
