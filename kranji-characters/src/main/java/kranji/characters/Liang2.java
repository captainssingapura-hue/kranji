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

/** Characters pronounced liang (tone 2). */
public final class Liang2 {
    private Liang2() {}

    /** 粮 (liang2) — grain; food. */
    public static final ChineseCharacterEntry 粮_GRAIN_FOOD = entry("粮")
            .py(L, I, Body.A, Tail.NG, T2).strokes(13).radical(119)
            .leftRight(zi("米"), zi("良"))
            .phonoSemantic(zi("米"), zi("良"));

    public static final List<ChineseCharacterEntry> ALL = List.of(粮_GRAIN_FOOD);
}
