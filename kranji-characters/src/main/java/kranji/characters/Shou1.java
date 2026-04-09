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

/** Characters pronounced shou (tone 1). */
public final class Shou1 {
    private Shou1() {}

    /** 收 (shou1) — receive; collect. */
    public static final ChineseCharacterEntry 收_RECEIVE_COLLECT = entry("收")
            .py(SH, OPEN, Body.O, Tail.VOWEL_U, T1).strokes(6).radical(66)
            .leftRight(zi("丩"), FAN_WEN_PANG)
            .phonoSemantic(FAN_WEN_PANG, zi("丩"));

    public static final List<ChineseCharacterEntry> ALL = List.of(收_RECEIVE_COLLECT);
}
