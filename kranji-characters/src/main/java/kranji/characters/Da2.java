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

/** Characters pronounced da (tone 2). */
public final class Da2 {
    private Da2() {}

    /** 达 (da2) — reach; achieve. */
    public static final ChineseCharacterEntry 达_REACH_ACHIEVE = entry("达")
            .py(D, OPEN, Body.A, Tail.NONE, T2).strokes(6).radical(162)
            .semiEnclosureBL(ZOU_ZHI_DI, zi("大"))
            .phonoSemantic(ZOU_ZHI_DI, zi("大"));

    public static final List<ChineseCharacterEntry> ALL = List.of(达_REACH_ACHIEVE);
}
