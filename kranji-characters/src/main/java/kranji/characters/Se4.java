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

/** Characters pronounced se (tone 4). */
public final class Se4 {
    private Se4() {}

    /** 色 (se4) — color. */
    public static final ChineseCharacterEntry 色_COLOR = entry("色")
            .py(S, OPEN, Body.E, Tail.NONE, T4).strokes(6).radical(139)
            .topBottom(zi("⺈"), zi("巴"))
            .compoundIndicative("color");

    public static final List<ChineseCharacterEntry> ALL = List.of(色_COLOR);
}
