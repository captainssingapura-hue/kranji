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

/** Characters pronounced bo (tone 2). */
public final class Bo2 {
    private Bo2() {}

    /** 伯 (bo2) — uncle (elder). */
    public static final ChineseCharacterEntry 伯_UNCLE_ELDER = entry("伯")
            .py(B, OPEN, Body.O, Tail.NONE, T2).strokes(7).radical(9)
            .leftRight(DAN_REN_PANG, zi("白"))
            .phonoSemantic(DAN_REN_PANG, zi("白"));

    /** 脖 (bo2) — neck. */
    public static final ChineseCharacterEntry 脖_NECK = entry("脖")
            .py(B, OPEN, Body.O, Tail.NONE, T2).strokes(10).radical(130)
            .leftRight(zi("月"), zi("孛"))
            .phonoSemantic(zi("月"), zi("孛"));

    public static final List<ChineseCharacterEntry> ALL = List.of(伯_UNCLE_ELDER, 脖_NECK);
}
