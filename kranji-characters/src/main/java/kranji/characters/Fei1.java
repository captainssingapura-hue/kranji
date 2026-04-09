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

/** Characters pronounced fei (tone 1). */
public final class Fei1 {
    private Fei1() {}

    /** 非 (fei1) — not; wrong. */
    public static final ChineseCharacterEntry 非_NOT_WRONG = entry("非")
            .py(F, OPEN, Body.E, Tail.VOWEL_I, T1).strokes(8).radical(175)
            .singular()
            .pictograph();

    /** 飞 (fei1) — fly. */
    public static final ChineseCharacterEntry 飞_FLY = entry("飞")
            .py(F, OPEN, Body.E, Tail.VOWEL_I, T1).strokes(3).radical(183)
            .singular()
            .pictograph();

    public static final List<ChineseCharacterEntry> ALL = List.of(非_NOT_WRONG, 飞_FLY);
}
