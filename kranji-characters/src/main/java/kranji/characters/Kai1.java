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

/** Characters pronounced kai (tone 1). */
public final class Kai1 {
    private Kai1() {}

    /** 开 (kai1) — open. */
    public static final ChineseCharacterEntry 开_OPEN = entry("开")
            .py(K, OPEN, Body.A, Tail.VOWEL_I, T1).strokes(4).radical(55)
            .singular()
            .compoundIndicative("open");

    public static final List<ChineseCharacterEntry> ALL = List.of(开_OPEN);
}
