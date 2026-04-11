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

/** Characters pronounced mian (tone 4). */
public final class Mian4 {
    private Mian4() {}

    /** 面 (mian4) — face; surface. */
    public static final ChineseCharacterEntry 面_FACE_SURFACE = entry("面")
            .py(M, I, Body.A, Tail.N, T4).strokes(9).radical(176)
            .singular()
            .pictograph();

    public static final List<ChineseCharacterEntry> ALL = List.of(面_FACE_SURFACE);
}
