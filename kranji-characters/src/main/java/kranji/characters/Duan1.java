package kranji.characters;

import kranji.entry.ChineseCharacterEntry;

import java.util.List;

import static kranji.characters.Comp.*;
import static kranji.characters.EntryBuilder.entry;
import static kranji.component.basic.BasicComponents.*;
import static kranji.pinyin.Initial.*;
import static kranji.pinyin.Head.*;
import kranji.pinyin.Body;
import kranji.pinyin.Tail;

/** Characters pronounced duan (tone 1). */
public final class Duan1 {
    private Duan1() {}

    /** 端 (duan1) — end; proper. */
    public static final ChineseCharacterEntry 端_END_PROPER = entry("端")
            .py(D, U, Body.A, Tail.N, T1).strokes(14).radical(117)
            .leftRight(zi("立"), zi("耑"))
            .phonoSemantic(zi("立"), zi("耑"));

    public static final List<ChineseCharacterEntry> ALL = List.of(端_END_PROPER);
}
