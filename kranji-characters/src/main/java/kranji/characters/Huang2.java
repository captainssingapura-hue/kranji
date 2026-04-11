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

/** Characters pronounced huang (tone 2). */
public final class Huang2 {
    private Huang2() {}

    /** 黄 (huang2) — yellow. */
    public static final ChineseCharacterEntry 黄_YELLOW = entry("黄")
            .py(H, U, Body.A, Tail.NG, T2).strokes(11).radical(201)
            .singular()
            .pictograph();

    public static final List<ChineseCharacterEntry> ALL = List.of(黄_YELLOW);
}
