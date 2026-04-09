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

/** Characters pronounced hao (tone 4). */
public final class Hao4 {
    private Hao4() {}

    /** 号 (hao4) — number; mark. */
    public static final ChineseCharacterEntry 号_NUMBER_MARK = entry("号")
            .py(H, OPEN, Body.A, Tail.VOWEL_U, T4).strokes(5).radical(30)
            .topBottom(zi("口"), zi("丂"))
            .phonoSemantic(zi("口"), zi("丂"));

    public static final List<ChineseCharacterEntry> ALL = List.of(号_NUMBER_MARK);
}
