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

/** Characters pronounced jin (tone 1). */
public final class Jin1 {
    private Jin1() {}

    /** 今 (jin1) — today; now. */
    public static final ChineseCharacterEntry 今_TODAY_NOW = entry("今")
            .py(J, OPEN, Body.I, Tail.N, T1).strokes(4).radical(9)
            .topBottom(zi("人"), zi("𠃌"))
            .compoundIndicative("today; now");

    /** 金 (jin1) — gold; metal. */
    public static final ChineseCharacterEntry 金_GOLD_METAL = entry("金")
            .py(J, OPEN, Body.I, Tail.N, T1).strokes(8).radical(167)
            .singular()
            .pictograph();

    public static final List<ChineseCharacterEntry> ALL = List.of(今_TODAY_NOW, 金_GOLD_METAL);
}
