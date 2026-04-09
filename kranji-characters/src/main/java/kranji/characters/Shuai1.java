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

/** Characters pronounced shuai (tone 1). */
public final class Shuai1 {
    private Shuai1() {}

    /** 摔 (shuai1) — fall; throw. */
    public static final ChineseCharacterEntry 摔_FALL_THROW = entry("摔")
            .py(SH, U, Body.A, Tail.VOWEL_I, T1).strokes(14).radical(64)
            .leftRight(TI_SHOU_PANG, zi("率"))
            .phonoSemantic(TI_SHOU_PANG, zi("率"));

    public static final List<ChineseCharacterEntry> ALL = List.of(摔_FALL_THROW);
}
