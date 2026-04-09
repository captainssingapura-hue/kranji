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

/** Characters pronounced bing (tone 4). */
public final class Bing4 {
    private Bing4() {}

    /** 病 (bing4) — ill; disease. */
    public static final ChineseCharacterEntry 病_ILL_DISEASE = entry("病")
            .py(B, OPEN, Body.I, Tail.NG, T4).strokes(10).radical(104)
            .semiEnclosureUL(BING_ZI_PANG, zi("丙"))
            .phonoSemantic(BING_ZI_PANG, zi("丙"));

    public static final List<ChineseCharacterEntry> ALL = List.of(病_ILL_DISEASE);
}
