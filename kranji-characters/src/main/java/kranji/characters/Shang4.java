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

/** Characters pronounced shang (tone 4). */
public final class Shang4 {
    private Shang4() {}

    /** 上 (shang) — above. Singular indicative. */
    public static final ChineseCharacterEntry 上_ABOVE = entry("上")
            .py(SH, OPEN, Body.A, Tail.NG, T4).strokes(3).radical(1)
            .singular()
            .indicative("stroke above the baseline marks 'above'");

    public static final List<ChineseCharacterEntry> ALL = List.of(上_ABOVE);
}
