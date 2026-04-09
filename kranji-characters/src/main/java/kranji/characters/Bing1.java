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

/** Characters pronounced bing (tone 1). */
public final class Bing1 {
    private Bing1() {}

    /** 冰 (bing1) — ice. */
    public static final ChineseCharacterEntry 冰_ICE = entry("冰")
            .py(B, OPEN, Body.I, Tail.NG, T1).strokes(6).radical(15)
            .leftRight(LIANG_DIAN_SHUI, zi("水"))
            .phonoSemantic(LIANG_DIAN_SHUI, zi("水"));

    public static final List<ChineseCharacterEntry> ALL = List.of(冰_ICE);
}
