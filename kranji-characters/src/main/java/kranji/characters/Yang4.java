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

/** Characters pronounced yang (tone 4). */
public final class Yang4 {
    private Yang4() {}

    /** 样 (yang4) — kind; manner. */
    public static final ChineseCharacterEntry 样_KIND_MANNER = entry("样")
            .py(ZERO, I, Body.A, Tail.NG, T4).strokes(10).radical(75)
            .leftRight(zi("木"), zi("羊"))
            .phonoSemantic(zi("木"), zi("羊"));

    public static final List<ChineseCharacterEntry> ALL = List.of(样_KIND_MANNER);
}
