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

/** Characters pronounced hao (tone 3). */
public final class Hao3 {
    private Hao3() {}

    /** 好 (hao3) — good. */
    public static final ChineseCharacterEntry 好_GOOD = entry("好")
            .py(H, OPEN, Body.A, Tail.VOWEL_U, T3).strokes(6).radical(38)
            .leftRight(zi("女"), zi("子"))
            .compoundIndicative("good");

    public static final List<ChineseCharacterEntry> ALL = List.of(好_GOOD);
}
