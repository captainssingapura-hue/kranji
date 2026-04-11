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

/** Characters pronounced duan (tone 4). */
public final class Duan4 {
    private Duan4() {}

    /** 段 (duan4) — section; piece. */
    public static final ChineseCharacterEntry 段_SECTION_PIECE = entry("段")
            .py(D, U, Body.A, Tail.N, T4).strokes(9).radical(79)
            .leftRight(zi("𠂤"), zi("殳"))
            .phonoSemantic(zi("殳"), zi("𠂤"));

    public static final List<ChineseCharacterEntry> ALL = List.of(段_SECTION_PIECE);
}
