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

/** Characters pronounced gua (tone 1). */
public final class Gua1 {
    private Gua1() {}

    /** 瓜 (gua1) — melon. */
    public static final ChineseCharacterEntry 瓜_MELON = entry("瓜")
            .py(G, U, Body.A, Tail.NONE, T1).strokes(5).radical(97)
            .singular()
            .pictograph();

    public static final List<ChineseCharacterEntry> ALL = List.of(瓜_MELON);
}
