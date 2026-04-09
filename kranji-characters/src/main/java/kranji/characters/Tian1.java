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

/** Characters pronounced tian (tone 1). */
public final class Tian1 {
    private Tian1() {}

    /** 天 (tian1) — sky; day. */
    public static final ChineseCharacterEntry 天_SKY_DAY = entry("天")
            .py(T, I, Body.A, Tail.N, T1).strokes(4).radical(37)
            .topBottom(zi("一"), zi("大"))
            .indicative("sky; day");

    public static final List<ChineseCharacterEntry> ALL = List.of(天_SKY_DAY);
}
