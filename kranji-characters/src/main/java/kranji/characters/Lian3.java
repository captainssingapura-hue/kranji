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

/** Characters pronounced lian (tone 3). */
public final class Lian3 {
    private Lian3() {}

    /** 脸 (lian3) — face. */
    public static final ChineseCharacterEntry 脸_FACE = entry("脸")
            .py(L, I, Body.A, Tail.N, T3).strokes(11).radical(130)
            .leftRight(zi("月"), zi("佥"))
            .phonoSemantic(zi("月"), zi("佥"));

    public static final List<ChineseCharacterEntry> ALL = List.of(脸_FACE);
}
