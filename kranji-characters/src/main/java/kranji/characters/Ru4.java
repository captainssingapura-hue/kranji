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

/** Characters pronounced ru (tone 4). */
public final class Ru4 {
    private Ru4() {}

    /** 入 (ru4) — enter. */
    public static final ChineseCharacterEntry 入_ENTER = entry("入")
            .py(R, U, Body.U, Tail.NONE, T4).strokes(2).radical(11)
            .singular()
            .pictograph();

    public static final List<ChineseCharacterEntry> ALL = List.of(入_ENTER);
}
