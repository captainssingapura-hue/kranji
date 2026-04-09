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

/** Characters pronounced yao (tone 2). */
public final class Yao2 {
    private Yao2() {}

    /** 遥 (yao2) — remote; distant. */
    public static final ChineseCharacterEntry 遥_REMOTE_DISTANT = entry("遥")
            .py(ZERO, I, Body.A, Tail.VOWEL_U, T2).strokes(13).radical(162)
            .semiEnclosureBL(ZOU_ZHI_DI, zi("摇"))
            .phonoSemantic(ZOU_ZHI_DI, zi("摇"));

    public static final List<ChineseCharacterEntry> ALL = List.of(遥_REMOTE_DISTANT);
}
