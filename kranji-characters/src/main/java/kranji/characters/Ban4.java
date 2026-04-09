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

/** Characters pronounced ban (tone 4). */
public final class Ban4 {
    private Ban4() {}

    /** 半 (ban4) — half. */
    public static final ChineseCharacterEntry 半_HALF = entry("半")
            .py(B, OPEN, Body.A, Tail.N, T4).strokes(5).radical(24)
            .singular()
            .indicative("half");

    /** 办 (ban4) — handle; do. */
    public static final ChineseCharacterEntry 办_HANDLE_DO = entry("办")
            .py(B, OPEN, Body.A, Tail.N, T4).strokes(4).radical(19)
            .singular()
            .compoundIndicative("handle; do");

    public static final List<ChineseCharacterEntry> ALL = List.of(半_HALF, 办_HANDLE_DO);
}
