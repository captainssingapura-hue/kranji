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

/** Characters pronounced zhao (tone 1). */
public final class Zhao1 {
    private Zhao1() {}

    /** 招 (zhao1) — recruit; beckon. */
    public static final ChineseCharacterEntry 招_RECRUIT_BECKON = entry("招")
            .py(ZH, OPEN, Body.A, Tail.VOWEL_U, T1).strokes(8).radical(64)
            .leftRight(TI_SHOU_PANG, zi("召"))
            .phonoSemantic(TI_SHOU_PANG, zi("召"));

    public static final List<ChineseCharacterEntry> ALL = List.of(招_RECRUIT_BECKON);
}
