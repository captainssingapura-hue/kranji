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

/** Characters pronounced hui (tone 3). */
public final class Hui3 {
    private Hui3() {}

    /** 悔 (hui3) — regret; repent. */
    public static final ChineseCharacterEntry 悔_REGRET_REPENT = entry("悔")
            .py(H, U, Body.E, Tail.VOWEL_I, T3).strokes(10).radical(61)
            .leftRight(SHU_XIN_PANG, zi("每"))
            .phonoSemantic(SHU_XIN_PANG, zi("每"));

    public static final List<ChineseCharacterEntry> ALL = List.of(悔_REGRET_REPENT);
}
