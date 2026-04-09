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

/** Characters pronounced xiang (tone 3). */
public final class Xiang3 {
    private Xiang3() {}

    /** 想 (xiang3) — think; want. */
    public static final ChineseCharacterEntry 想_THINK_WANT = entry("想")
            .py(X, I, Body.A, Tail.NG, T3).strokes(13).radical(61)
            .topBottom(zi("相"), zi("心"))
            .phonoSemantic(zi("心"), zi("相"));

    /** 响 (xiang3) — sound; echo. */
    public static final ChineseCharacterEntry 响_SOUND_ECHO = entry("响")
            .py(X, I, Body.A, Tail.NG, T3).strokes(9).radical(30)
            .leftRight(zi("口"), zi("向"))
            .phonoSemantic(zi("口"), zi("向"));

    public static final List<ChineseCharacterEntry> ALL = List.of(想_THINK_WANT, 响_SOUND_ECHO);
}
