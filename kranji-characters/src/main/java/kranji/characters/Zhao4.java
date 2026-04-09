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

/** Characters pronounced zhao (tone 4). */
public final class Zhao4 {
    private Zhao4() {}

    /** 照 (zhao4) — shine; photo. */
    public static final ChineseCharacterEntry 照_SHINE_PHOTO = entry("照")
            .py(ZH, OPEN, Body.A, Tail.VOWEL_U, T4).strokes(13).radical(86)
            .topBottom(zi("昭"), SI_DIAN_DI)
            .phonoSemantic(SI_DIAN_DI, zi("昭"));

    public static final List<ChineseCharacterEntry> ALL = List.of(照_SHINE_PHOTO);
}
