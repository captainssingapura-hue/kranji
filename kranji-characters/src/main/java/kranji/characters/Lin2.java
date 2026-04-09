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

/** Characters pronounced lin (tone 2). */
public final class Lin2 {
    private Lin2() {}

    /** 林 (lin) — forest. LeftRight: 木 + 木. Compound indicative. */
    public static final ChineseCharacterEntry 林_FOREST = entry("林")
            .py(L, OPEN, Body.I, Tail.N, T2).strokes(8).radical(75)
            .leftRight(zi("木"), zi("木"))
            .compoundIndicative("木(tree) + 木(tree) → forest");

    public static final List<ChineseCharacterEntry> ALL = List.of(林_FOREST);
}
