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

/** Characters pronounced nao (tone 3). */
public final class Nao3 {
    private Nao3() {}

    /** 脑 (nao3) — brain. */
    public static final ChineseCharacterEntry 脑_BRAIN = entry("脑")
            .py(N, OPEN, Body.A, Tail.VOWEL_U, T3).strokes(10).radical(130)
            .leftRight(zi("月"), zi("㐬"))
            .phonoSemantic(zi("月"), zi("㐬"));

    public static final List<ChineseCharacterEntry> ALL = List.of(脑_BRAIN);
}
