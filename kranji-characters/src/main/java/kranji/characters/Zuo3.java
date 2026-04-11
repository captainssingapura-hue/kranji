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

/** Characters pronounced zuo (tone 3). */
public final class Zuo3 {
    private Zuo3() {}

    /** 左 (zuo3) — left (side). */
    public static final ChineseCharacterEntry 左_LEFT_SIDE = entry("左")
            .py(Z, U, Body.O, Tail.NONE, T3).strokes(5).radical(48)
            .topBottom(zi("𠂇"), zi("工"))
            .compoundIndicative("left (side)");

    public static final List<ChineseCharacterEntry> ALL = List.of(左_LEFT_SIDE);
}
