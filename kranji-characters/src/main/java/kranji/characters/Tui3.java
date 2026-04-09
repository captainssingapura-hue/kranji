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

/** Characters pronounced tui (tone 3). */
public final class Tui3 {
    private Tui3() {}

    /** 腿 (tui3) — leg. */
    public static final ChineseCharacterEntry 腿_LEG = entry("腿")
            .py(T, U, Body.E, Tail.VOWEL_I, T3).strokes(13).radical(130)
            .leftRight(zi("月"), zi("退"))
            .phonoSemantic(zi("月"), zi("退"));

    public static final List<ChineseCharacterEntry> ALL = List.of(腿_LEG);
}
