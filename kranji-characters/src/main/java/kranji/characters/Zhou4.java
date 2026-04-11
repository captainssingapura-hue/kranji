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

/** Characters pronounced zhou (tone 4). */
public final class Zhou4 {
    private Zhou4() {}

    /** 宙 (zhou4) — cosmos; time. */
    public static final ChineseCharacterEntry 宙_COSMOS_TIME = entry("宙")
            .py(ZH, OPEN, Body.O, Tail.VOWEL_U, T4).strokes(8).radical(40)
            .topBottom(BAO_GAI_TOU, zi("由"))
            .phonoSemantic(BAO_GAI_TOU, zi("由"));

    public static final List<ChineseCharacterEntry> ALL = List.of(宙_COSMOS_TIME);
}
