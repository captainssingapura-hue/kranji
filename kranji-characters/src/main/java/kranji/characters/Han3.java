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

/** Characters pronounced han (tone 3). */
public final class Han3 {
    private Han3() {}

    /** 喊 (han3) — shout; yell. */
    public static final ChineseCharacterEntry 喊_SHOUT_YELL = entry("喊")
            .py(H, OPEN, Body.A, Tail.N, T3).strokes(12).radical(30)
            .leftRight(zi("口"), zi("咸"))
            .phonoSemantic(zi("口"), zi("咸"));

    public static final List<ChineseCharacterEntry> ALL = List.of(喊_SHOUT_YELL);
}
