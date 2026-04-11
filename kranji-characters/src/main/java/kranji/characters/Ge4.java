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

/** Characters pronounced ge (tone 4). */
public final class Ge4 {
    private Ge4() {}

    /** 个 (ge4) — measure word. */
    public static final ChineseCharacterEntry 个_MEASURE_WORD = entry("个")
            .py(G, OPEN, Body.E, Tail.NONE, T4).strokes(3).radical(9)
            .topBottom(zi("人"), zi("丨"))
            .indicative("measure word");

    /** 各 (ge4) — each; every. */
    public static final ChineseCharacterEntry 各_EACH_EVERY = entry("各")
            .py(G, OPEN, Body.E, Tail.NONE, T4).strokes(6).radical(30)
            .topBottom(zi("夂"), zi("口"))
            .compoundIndicative("each; every");

    public static final List<ChineseCharacterEntry> ALL = List.of(个_MEASURE_WORD, 各_EACH_EVERY);
}
