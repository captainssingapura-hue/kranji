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

/** Characters pronounced yong (tone 3). */
public final class Yong3 {
    private Yong3() {}

    /** 永 (yong3) — forever. */
    public static final ChineseCharacterEntry 永_FOREVER = entry("永")
            .py(ZERO, I, Body.O, Tail.NG, T3).strokes(5).radical(85)
            .singular()
            .pictograph();

    /** 勇 (yong3) — brave; courage. */
    public static final ChineseCharacterEntry 勇_BRAVE_COURAGE = entry("勇")
            .py(ZERO, I, Body.O, Tail.NG, T3).strokes(9).radical(19)
            .topBottom(zi("甬"), zi("力"))
            .phonoSemantic(zi("力"), zi("甬"));

    public static final List<ChineseCharacterEntry> ALL = List.of(永_FOREVER, 勇_BRAVE_COURAGE);
}
