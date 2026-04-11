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

/** Characters pronounced kua (tone 1). */
public final class Kua1 {
    private Kua1() {}

    /** 夸 (kua1) — boast; praise. */
    public static final ChineseCharacterEntry 夸_BOAST_PRAISE = entry("夸")
            .py(K, U, Body.A, Tail.NONE, T1).strokes(6).radical(37)
            .topBottom(zi("大"), zi("亏"))
            .compoundIndicative("boast; praise");

    public static final List<ChineseCharacterEntry> ALL = List.of(夸_BOAST_PRAISE);
}
