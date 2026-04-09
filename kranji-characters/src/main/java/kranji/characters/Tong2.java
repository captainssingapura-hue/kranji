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

/** Characters pronounced tong (tone 2). */
public final class Tong2 {
    private Tong2() {}

    /** 同 (tong2) — same. */
    public static final ChineseCharacterEntry 同_SAME = entry("同")
            .py(T, OPEN, Body.O, Tail.NG, T2).strokes(6).radical(30)
            .semiEnclosureT3(TONG_ZI_KUANG, zi("口"))
            .compoundIndicative("same");

    public static final List<ChineseCharacterEntry> ALL = List.of(同_SAME);
}
