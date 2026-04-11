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

/** Characters pronounced luo (tone 4). */
public final class Luo4 {
    private Luo4() {}

    /** 落 (luo4) — fall; drop. */
    public static final ChineseCharacterEntry 落_FALL_DROP = entry("落")
            .py(L, U, Body.O, Tail.NONE, T4).strokes(12).radical(140)
            .topBottom(CAO_ZI_TOU, zi("洛"))
            .phonoSemantic(CAO_ZI_TOU, zi("洛"));

    public static final List<ChineseCharacterEntry> ALL = List.of(落_FALL_DROP);
}
