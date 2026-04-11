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

/** Characters pronounced ben (tone 3). */
public final class Ben3 {
    private Ben3() {}

    /** 本 (ben3) — root; origin. */
    public static final ChineseCharacterEntry 本_ROOT_ORIGIN = entry("本")
            .py(B, OPEN, Body.E, Tail.N, T3).strokes(5).radical(75)
            .singular()
            .indicative("root; origin");

    public static final List<ChineseCharacterEntry> ALL = List.of(本_ROOT_ORIGIN);
}
