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

/** Characters pronounced zhuan (tone 1). */
public final class Zhuan1 {
    private Zhuan1() {}

    /** 砖 (zhuan1) — brick. */
    public static final ChineseCharacterEntry 砖_BRICK = entry("砖")
            .py(ZH, U, Body.A, Tail.N, T1).strokes(9).radical(112)
            .leftRight(zi("石"), zi("专"))
            .phonoSemantic(zi("石"), zi("专"));

    public static final List<ChineseCharacterEntry> ALL = List.of(砖_BRICK);
}
