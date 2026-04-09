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

/** Characters pronounced fa (tone 3). */
public final class Fa3 {
    private Fa3() {}

    /** 法 (fa3) — law; method. */
    public static final ChineseCharacterEntry 法_LAW_METHOD = entry("法")
            .py(F, OPEN, Body.A, Tail.NONE, T3).strokes(8).radical(85)
            .leftRight(SAN_DIAN_SHUI, zi("去"))
            .phonoSemantic(SAN_DIAN_SHUI, zi("去"));

    public static final List<ChineseCharacterEntry> ALL = List.of(法_LAW_METHOD);
}
