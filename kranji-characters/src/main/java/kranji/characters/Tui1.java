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

/** Characters pronounced tui (tone 1). */
public final class Tui1 {
    private Tui1() {}

    /** 推 (tui1) — push. */
    public static final ChineseCharacterEntry 推_PUSH = entry("推")
            .py(T, U, Body.E, Tail.VOWEL_I, T1).strokes(11).radical(64)
            .leftRight(TI_SHOU_PANG, zi("隹"))
            .phonoSemantic(TI_SHOU_PANG, zi("隹"));

    public static final List<ChineseCharacterEntry> ALL = List.of(推_PUSH);
}
