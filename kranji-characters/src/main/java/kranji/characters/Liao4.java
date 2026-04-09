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

/** Characters pronounced liao (tone 4). */
public final class Liao4 {
    private Liao4() {}

    /** 料 (liao4) — material. */
    public static final ChineseCharacterEntry 料_MATERIAL = entry("料")
            .py(L, I, Body.A, Tail.VOWEL_U, T4).strokes(10).radical(68)
            .leftRight(zi("米"), zi("斗"))
            .compoundIndicative("material");

    public static final List<ChineseCharacterEntry> ALL = List.of(料_MATERIAL);
}
