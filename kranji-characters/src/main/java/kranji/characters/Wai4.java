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

/** Characters pronounced wai (tone 4). */
public final class Wai4 {
    private Wai4() {}

    /** 外 (wai4) — outside. */
    public static final ChineseCharacterEntry 外_OUTSIDE = entry("外")
            .py(ZERO, U, Body.A, Tail.VOWEL_I, T4).strokes(5).radical(36)
            .leftRight(zi("夕"), zi("卜"))
            .compoundIndicative("outside");

    public static final List<ChineseCharacterEntry> ALL = List.of(外_OUTSIDE);
}
