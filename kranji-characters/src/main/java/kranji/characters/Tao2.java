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

/** Characters pronounced tao (tone 2). */
public final class Tao2 {
    private Tao2() {}

    /** 桃 (tao2) — peach. */
    public static final ChineseCharacterEntry 桃_PEACH = entry("桃")
            .py(T, OPEN, Body.A, Tail.VOWEL_U, T2).strokes(10).radical(75)
            .leftRight(zi("木"), zi("兆"))
            .phonoSemantic(zi("木"), zi("兆"));

    public static final List<ChineseCharacterEntry> ALL = List.of(桃_PEACH);
}
