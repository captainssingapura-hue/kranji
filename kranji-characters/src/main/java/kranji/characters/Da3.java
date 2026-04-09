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

/** Characters pronounced da (tone 3). */
public final class Da3 {
    private Da3() {}

    /** 打 (da3) — hit; strike. */
    public static final ChineseCharacterEntry 打_HIT_STRIKE = entry("打")
            .py(D, OPEN, Body.A, Tail.NONE, T3).strokes(5).radical(64)
            .leftRight(TI_SHOU_PANG, zi("丁"))
            .phonoSemantic(TI_SHOU_PANG, zi("丁"));

    public static final List<ChineseCharacterEntry> ALL = List.of(打_HIT_STRIKE);
}
