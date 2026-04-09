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

/** Characters pronounced hui (tone 1). */
public final class Hui1 {
    private Hui1() {}

    /** 恢 (hui1) — restore. */
    public static final ChineseCharacterEntry 恢_RESTORE = entry("恢")
            .py(H, U, Body.E, Tail.VOWEL_I, T1).strokes(9).radical(61)
            .leftRight(SHU_XIN_PANG, zi("灰"))
            .phonoSemantic(SHU_XIN_PANG, zi("灰"));

    public static final List<ChineseCharacterEntry> ALL = List.of(恢_RESTORE);
}
