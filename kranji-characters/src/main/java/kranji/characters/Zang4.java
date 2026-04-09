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

/** Characters pronounced zang (tone 4). */
public final class Zang4 {
    private Zang4() {}

    /** 脏 (zang4) — dirty; organ. */
    public static final ChineseCharacterEntry 脏_DIRTY_ORGAN = entry("脏")
            .py(Z, OPEN, Body.A, Tail.NG, T4).strokes(10).radical(130)
            .leftRight(zi("月"), zi("庄"))
            .phonoSemantic(zi("月"), zi("庄"));

    public static final List<ChineseCharacterEntry> ALL = List.of(脏_DIRTY_ORGAN);
}
