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

/** Characters pronounced dui (tone 4). */
public final class Dui4 {
    private Dui4() {}

    /** 对 (dui4) — correct; pair. */
    public static final ChineseCharacterEntry 对_CORRECT_PAIR = entry("对")
            .py(D, U, Body.E, Tail.VOWEL_I, T4).strokes(5).radical(41)
            .leftRight(zi("又"), zi("寸"))
            .phonoSemantic(zi("寸"), zi("又"));

    /** 队 (dui4) — team; line. */
    public static final ChineseCharacterEntry 队_TEAM_LINE = entry("队")
            .py(D, U, Body.E, Tail.VOWEL_I, T4).strokes(4).radical(170)
            .leftRight(zi("阝"), zi("人"))
            .phonoSemantic(zi("阝"), zi("人"));

    public static final List<ChineseCharacterEntry> ALL = List.of(对_CORRECT_PAIR, 队_TEAM_LINE);
}
