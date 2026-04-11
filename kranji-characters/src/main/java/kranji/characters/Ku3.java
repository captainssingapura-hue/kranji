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

/** Characters pronounced ku (tone 3). */
public final class Ku3 {
    private Ku3() {}

    /** 苦 (ku3) — bitter. */
    public static final ChineseCharacterEntry 苦_BITTER = entry("苦")
            .py(K, U, Body.U, Tail.NONE, T3).strokes(8).radical(140)
            .topBottom(CAO_ZI_TOU, zi("古"))
            .phonoSemantic(CAO_ZI_TOU, zi("古"));

    public static final List<ChineseCharacterEntry> ALL = List.of(苦_BITTER);
}
