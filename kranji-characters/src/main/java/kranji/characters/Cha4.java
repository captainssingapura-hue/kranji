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

/** Characters pronounced cha (tone 4). */
public final class Cha4 {
    private Cha4() {}

    /** 差 (cha4) — differ; poor. */
    public static final ChineseCharacterEntry 差_DIFFER_POOR = entry("差")
            .py(CH, OPEN, Body.A, Tail.NONE, T4).strokes(9).radical(48)
            .topBottom(zi("羊"), zi("工"))
            .compoundIndicative("differ; poor");

    public static final List<ChineseCharacterEntry> ALL = List.of(差_DIFFER_POOR);
}
