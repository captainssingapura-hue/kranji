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

/** Characters pronounced yong (tone 1). */
public final class Yong1 {
    private Yong1() {}

    /** 拥 (yong1) — embrace; own. */
    public static final ChineseCharacterEntry 拥_EMBRACE_OWN = entry("拥")
            .py(ZERO, I, Body.O, Tail.NG, T1).strokes(8).radical(64)
            .leftRight(TI_SHOU_PANG, zi("用"))
            .phonoSemantic(TI_SHOU_PANG, zi("用"));

    public static final List<ChineseCharacterEntry> ALL = List.of(拥_EMBRACE_OWN);
}
