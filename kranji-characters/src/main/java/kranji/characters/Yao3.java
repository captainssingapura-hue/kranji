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

/** Characters pronounced yao (tone 3). */
public final class Yao3 {
    private Yao3() {}

    /** 咬 (yao3) — bite. */
    public static final ChineseCharacterEntry 咬_BITE = entry("咬")
            .py(ZERO, I, Body.A, Tail.VOWEL_U, T3).strokes(9).radical(30)
            .leftRight(zi("口"), zi("交"))
            .phonoSemantic(zi("口"), zi("交"));

    public static final List<ChineseCharacterEntry> ALL = List.of(咬_BITE);
}
