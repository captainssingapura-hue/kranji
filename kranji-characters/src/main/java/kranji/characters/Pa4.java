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

/** Characters pronounced pa (tone 4). */
public final class Pa4 {
    private Pa4() {}

    /** 怕 (pa4) — fear; afraid. */
    public static final ChineseCharacterEntry 怕_FEAR_AFRAID = entry("怕")
            .py(P, OPEN, Body.A, Tail.NONE, T4).strokes(8).radical(61)
            .leftRight(SHU_XIN_PANG, zi("白"))
            .phonoSemantic(SHU_XIN_PANG, zi("白"));

    public static final List<ChineseCharacterEntry> ALL = List.of(怕_FEAR_AFRAID);
}
