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

/** Characters pronounced xiao (tone 4). */
public final class Xiao4 {
    private Xiao4() {}

    /** 笑 (xiao4) — laugh; smile. */
    public static final ChineseCharacterEntry 笑_LAUGH_SMILE = entry("笑")
            .py(X, I, Body.A, Tail.VOWEL_U, T4).strokes(10).radical(118)
            .topBottom(ZHU_ZI_TOU, zi("夭"))
            .phonoSemantic(ZHU_ZI_TOU, zi("夭"));

    public static final List<ChineseCharacterEntry> ALL = List.of(笑_LAUGH_SMILE);
}
