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

/** Characters pronounced an (tone 1). */
public final class An1 {
    private An1() {}

    /** 安 (an1) — peace; safe. */
    public static final ChineseCharacterEntry 安_PEACE_SAFE = entry("安")
            .py(ZERO, OPEN, Body.A, Tail.N, T1).strokes(6).radical(40)
            .topBottom(BAO_GAI_TOU, zi("女"))
            .compoundIndicative("peace; safe");

    public static final List<ChineseCharacterEntry> ALL = List.of(安_PEACE_SAFE);
}
