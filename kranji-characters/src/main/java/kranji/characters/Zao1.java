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

/** Characters pronounced zao (tone 1). */
public final class Zao1 {
    private Zao1() {}

    /** 遭 (zao1) — encounter. */
    public static final ChineseCharacterEntry 遭_ENCOUNTER = entry("遭")
            .py(Z, OPEN, Body.A, Tail.VOWEL_U, T1).strokes(14).radical(162)
            .semiEnclosureBL(ZOU_ZHI_DI, zi("曹"))
            .phonoSemantic(ZOU_ZHI_DI, zi("曹"));

    public static final List<ChineseCharacterEntry> ALL = List.of(遭_ENCOUNTER);
}
