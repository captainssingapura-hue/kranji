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

/** Characters pronounced heng (tone 2). */
public final class Heng2 {
    private Heng2() {}

    /** 横 (heng2) — horizontal. */
    public static final ChineseCharacterEntry 横_HORIZONTAL = entry("横")
            .py(H, OPEN, Body.E, Tail.NG, T2).strokes(15).radical(75)
            .leftRight(zi("木"), zi("黄"))
            .phonoSemantic(zi("木"), zi("黄"));

    public static final List<ChineseCharacterEntry> ALL = List.of(横_HORIZONTAL);
}
