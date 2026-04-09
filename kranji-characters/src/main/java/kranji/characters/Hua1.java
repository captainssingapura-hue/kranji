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

/** Characters pronounced hua (tone 1). */
public final class Hua1 {
    private Hua1() {}

    /** 花 (hua) — flower. TopBottom: 艹 + 化. Phono-semantic. */
    public static final ChineseCharacterEntry 花_FLOWER = entry("花")
            .py(H, U, Body.A, Tail.NONE, T1).strokes(7).radical(140)
            .topBottom(CAO_ZI_TOU, zi("化"))
            .phonoSemantic(CAO_ZI_TOU, zi("化"));

    public static final List<ChineseCharacterEntry> ALL = List.of(花_FLOWER);
}
