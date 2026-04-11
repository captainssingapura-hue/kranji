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

/** Characters pronounced pian (tone 4). */
public final class Pian4 {
    private Pian4() {}

    /** 片 (pian4) — piece; slice. */
    public static final ChineseCharacterEntry 片_PIECE_SLICE = entry("片")
            .py(P, I, Body.A, Tail.N, T4).strokes(4).radical(91)
            .singular()
            .pictograph();

    /** 骗 (pian4) — cheat; trick. */
    public static final ChineseCharacterEntry 骗_CHEAT_TRICK = entry("骗")
            .py(P, I, Body.A, Tail.N, T4).strokes(12).radical(187)
            .leftRight(zi("马"), zi("扁"))
            .phonoSemantic(zi("马"), zi("扁"));

    public static final List<ChineseCharacterEntry> ALL = List.of(片_PIECE_SLICE, 骗_CHEAT_TRICK);
}
