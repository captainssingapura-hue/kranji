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

/** Characters pronounced mu (tone 4). */
public final class Mu4 {
    private Mu4() {}

    /** 木 (mu) — tree. Singular pictograph. */
    public static final ChineseCharacterEntry 木_TREE = entry("木")
            .py(M, U, Body.U, Tail.NONE, T4).strokes(4).radical(75)
            .singular()
            .pictograph();

    public static final List<ChineseCharacterEntry> ALL = List.of(木_TREE);
}
