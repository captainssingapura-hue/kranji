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

/** Characters pronounced xin (tone 1). */
public final class Xin1 {
    private Xin1() {}

    /** 心 (xin1) — heart. */
    public static final ChineseCharacterEntry 心_HEART = entry("心")
            .py(X, OPEN, Body.I, Tail.N, T1).strokes(4).radical(61)
            .singular()
            .pictograph();

    /** 新 (xin1) — new. */
    public static final ChineseCharacterEntry 新_NEW = entry("新")
            .py(X, OPEN, Body.I, Tail.N, T1).strokes(13).radical(69)
            .leftRight(zi("亲"), zi("斤"))
            .phonoSemantic(zi("斤"), zi("亲"));

    public static final List<ChineseCharacterEntry> ALL = List.of(心_HEART, 新_NEW);
}
