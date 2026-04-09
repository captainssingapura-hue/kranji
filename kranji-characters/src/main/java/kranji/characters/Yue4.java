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

/** Characters pronounced yue (tone 4). */
public final class Yue4 {
    private Yue4() {}

    /** 月 (yue) — moon. Singular pictograph. */
    public static final ChineseCharacterEntry 月_MOON = entry("月")
            .py(ZERO, V, Body.E_CARON, Tail.NONE, T4).strokes(4).radical(74)
            .singular()
            .pictograph();

    public static final List<ChineseCharacterEntry> ALL = List.of(月_MOON);
}
