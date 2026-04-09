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

/** Characters pronounced shao (tone 1). */
public final class Shao1 {
    private Shao1() {}

    /** 烧 (shao1) — burn; cook. */
    public static final ChineseCharacterEntry 烧_BURN_COOK = entry("烧")
            .py(SH, OPEN, Body.A, Tail.VOWEL_U, T1).strokes(10).radical(86)
            .leftRight(zi("火"), zi("尧"))
            .phonoSemantic(zi("火"), zi("尧"));

    public static final List<ChineseCharacterEntry> ALL = List.of(烧_BURN_COOK);
}
