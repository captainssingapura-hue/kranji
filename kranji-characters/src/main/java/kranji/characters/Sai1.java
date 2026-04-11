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

/** Characters pronounced sai (tone 1). */
public final class Sai1 {
    private Sai1() {}

    /** 塞 (sai1) — stuff; block. */
    public static final ChineseCharacterEntry 塞_STUFF_BLOCK = entry("塞")
            .py(S, OPEN, Body.A, Tail.VOWEL_I, T1).strokes(13).radical(32)
            .topBottom(zi("宀土"), zi("土"))
            .compoundIndicative("stuff; block");

    public static final List<ChineseCharacterEntry> ALL = List.of(塞_STUFF_BLOCK);
}
