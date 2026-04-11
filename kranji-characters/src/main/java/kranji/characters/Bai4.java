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

/** Characters pronounced bai (tone 4). */
public final class Bai4 {
    private Bai4() {}

    /** 败 (bai4) — defeat; fail. */
    public static final ChineseCharacterEntry 败_DEFEAT_FAIL = entry("败")
            .py(B, OPEN, Body.A, Tail.VOWEL_I, T4).strokes(8).radical(154)
            .leftRight(zi("贝"), FAN_WEN_PANG)
            .compoundIndicative("defeat; fail");

    public static final List<ChineseCharacterEntry> ALL = List.of(败_DEFEAT_FAIL);
}
