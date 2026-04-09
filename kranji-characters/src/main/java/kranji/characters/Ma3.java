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

/** Characters pronounced ma (tone 3). */
public final class Ma3 {
    private Ma3() {}

    /** 马 (ma3) — horse. */
    public static final ChineseCharacterEntry 马_HORSE = entry("马")
            .py(M, OPEN, Body.A, Tail.NONE, T3).strokes(3).radical(187)
            .singular()
            .pictograph();

    /** 蚂 (ma3) — ant. */
    public static final ChineseCharacterEntry 蚂_ANT = entry("蚂")
            .py(M, OPEN, Body.A, Tail.NONE, T3).strokes(9).radical(142)
            .leftRight(zi("虫"), zi("马"))
            .phonoSemantic(zi("虫"), zi("马"));

    public static final List<ChineseCharacterEntry> ALL = List.of(马_HORSE, 蚂_ANT);
}
