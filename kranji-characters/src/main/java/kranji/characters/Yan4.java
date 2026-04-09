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

/** Characters pronounced yan (tone 4). */
public final class Yan4 {
    private Yan4() {}

    /** 验 (yan4) — verify; test. */
    public static final ChineseCharacterEntry 验_VERIFY_TEST = entry("验")
            .py(ZERO, I, Body.A, Tail.N, T4).strokes(10).radical(187)
            .leftRight(zi("马"), zi("佥"))
            .phonoSemantic(zi("马"), zi("佥"));

    public static final List<ChineseCharacterEntry> ALL = List.of(验_VERIFY_TEST);
}
