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

/** Characters pronounced mo (tone 1). */
public final class Mo1 {
    private Mo1() {}

    /** 摸 (mo1) — touch; feel. */
    public static final ChineseCharacterEntry 摸_TOUCH_FEEL = entry("摸")
            .py(M, OPEN, Body.O, Tail.NONE, T1).strokes(13).radical(64)
            .leftRight(TI_SHOU_PANG, zi("莫"))
            .phonoSemantic(TI_SHOU_PANG, zi("莫"));

    public static final List<ChineseCharacterEntry> ALL = List.of(摸_TOUCH_FEEL);
}
