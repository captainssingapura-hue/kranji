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

/** Characters pronounced pin (tone 2). */
public final class Pin2 {
    private Pin2() {}

    /** 贫 (pin2) — poor. */
    public static final ChineseCharacterEntry 贫_POOR = entry("贫")
            .py(P, OPEN, Body.I, Tail.N, T2).strokes(8).radical(154)
            .topBottom(zi("分"), zi("贝"))
            .phonoSemantic(zi("贝"), zi("分"));

    public static final List<ChineseCharacterEntry> ALL = List.of(贫_POOR);
}
