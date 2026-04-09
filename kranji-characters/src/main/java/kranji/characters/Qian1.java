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

/** Characters pronounced qian (tone 1). */
public final class Qian1 {
    private Qian1() {}

    /** 千 (qian1) — thousand. */
    public static final ChineseCharacterEntry 千_THOUSAND = entry("千")
            .py(Q, I, Body.A, Tail.N, T1).strokes(3).radical(24)
            .singular()
            .indicative("thousand");

    /** 签 (qian1) — sign; label. */
    public static final ChineseCharacterEntry 签_SIGN_LABEL = entry("签")
            .py(Q, I, Body.A, Tail.N, T1).strokes(13).radical(118)
            .topBottom(ZHU_ZI_TOU, zi("佥"))
            .phonoSemantic(ZHU_ZI_TOU, zi("佥"));

    public static final List<ChineseCharacterEntry> ALL = List.of(千_THOUSAND, 签_SIGN_LABEL);
}
