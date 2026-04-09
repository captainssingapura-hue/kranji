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

/** Characters pronounced zhe (tone 2). */
public final class Zhe2 {
    private Zhe2() {}

    /** 折 (zhe2) — fold; break. */
    public static final ChineseCharacterEntry 折_FOLD_BREAK = entry("折")
            .py(ZH, OPEN, Body.E, Tail.NONE, T2).strokes(7).radical(64)
            .leftRight(TI_SHOU_PANG, zi("斤"))
            .phonoSemantic(TI_SHOU_PANG, zi("斤"));

    public static final List<ChineseCharacterEntry> ALL = List.of(折_FOLD_BREAK);
}
