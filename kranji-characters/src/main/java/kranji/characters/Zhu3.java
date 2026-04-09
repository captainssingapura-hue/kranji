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

/** Characters pronounced zhu (tone 3). */
public final class Zhu3 {
    private Zhu3() {}

    /** 主 (zhu3) — main; lord. */
    public static final ChineseCharacterEntry 主_MAIN_LORD = entry("主")
            .py(ZH, U, Body.U, Tail.NONE, T3).strokes(5).radical(3)
            .singular()
            .indicative("main; lord");

    public static final List<ChineseCharacterEntry> ALL = List.of(主_MAIN_LORD);
}
