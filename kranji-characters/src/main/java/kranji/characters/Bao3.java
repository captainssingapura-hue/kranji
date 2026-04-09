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

/** Characters pronounced bao (tone 3). */
public final class Bao3 {
    private Bao3() {}

    /** 保 (bao3) — protect; keep. */
    public static final ChineseCharacterEntry 保_PROTECT_KEEP = entry("保")
            .py(B, OPEN, Body.A, Tail.VOWEL_U, T3).strokes(9).radical(9)
            .leftRight(DAN_REN_PANG, zi("呆"))
            .phonoSemantic(DAN_REN_PANG, zi("呆"));

    /** 饱 (bao3) — full (stomach). */
    public static final ChineseCharacterEntry 饱_FULL_STOMACH = entry("饱")
            .py(B, OPEN, Body.A, Tail.VOWEL_U, T3).strokes(8).radical(184)
            .leftRight(SHI_ZI_PANG, zi("包"))
            .phonoSemantic(SHI_ZI_PANG, zi("包"));

    /** 宝 (bao3) — treasure. */
    public static final ChineseCharacterEntry 宝_TREASURE = entry("宝")
            .py(B, OPEN, Body.A, Tail.VOWEL_U, T3).strokes(8).radical(40)
            .topBottom(BAO_GAI_TOU, zi("玉"))
            .compoundIndicative("treasure");

    public static final List<ChineseCharacterEntry> ALL = List.of(保_PROTECT_KEEP, 饱_FULL_STOMACH, 宝_TREASURE);
}
