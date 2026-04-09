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

/** Characters pronounced dong (tone 1). */
public final class Dong1 {
    private Dong1() {}

    /** 东 (dong1) — east. */
    public static final ChineseCharacterEntry 东_EAST = entry("东")
            .py(D, OPEN, Body.O, Tail.NG, T1).strokes(5).radical(75)
            .singular()
            .pictograph();

    /** 冬 (dong1) — winter. */
    public static final ChineseCharacterEntry 冬_WINTER = entry("冬")
            .py(D, OPEN, Body.O, Tail.NG, T1).strokes(5).radical(15)
            .topBottom(zi("夂"), LIANG_DIAN_SHUI)
            .compoundIndicative("winter");

    public static final List<ChineseCharacterEntry> ALL = List.of(东_EAST, 冬_WINTER);
}
