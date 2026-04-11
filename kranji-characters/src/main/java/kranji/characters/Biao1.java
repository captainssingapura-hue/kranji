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

/** Characters pronounced biao (tone 1). */
public final class Biao1 {
    private Biao1() {}

    /** 标 (biao1) — mark; sign. */
    public static final ChineseCharacterEntry 标_MARK_SIGN = entry("标")
            .py(B, I, Body.A, Tail.VOWEL_U, T1).strokes(9).radical(75)
            .leftRight(MU, zi("示"))
            .phonoSemantic(MU, zi("示"));

    public static final List<ChineseCharacterEntry> ALL = List.of(标_MARK_SIGN);
}
