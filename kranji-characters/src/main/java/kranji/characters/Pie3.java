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

/** Characters pronounced pie (tone 3). */
public final class Pie3 {
    private Pie3() {}

    /** 撇 (pie3) — left-falling. */
    public static final ChineseCharacterEntry 撇_LEFT_FALLING = entry("撇")
            .py(P, I, Body.E_CARON, Tail.NONE, T3).strokes(14).radical(64)
            .leftRight(TI_SHOU_PANG, zi("敝"))
            .phonoSemantic(TI_SHOU_PANG, zi("敝"));

    public static final List<ChineseCharacterEntry> ALL = List.of(撇_LEFT_FALLING);
}
