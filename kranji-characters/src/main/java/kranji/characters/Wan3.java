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

/** Characters pronounced wan (tone 3). */
public final class Wan3 {
    private Wan3() {}

    /** 碗 (wan3) — bowl. */
    public static final ChineseCharacterEntry 碗_BOWL = entry("碗")
            .py(ZERO, U, Body.A, Tail.N, T3).strokes(13).radical(112)
            .leftRight(zi("石"), zi("宛"))
            .phonoSemantic(zi("石"), zi("宛"));

    /** 晚 (wan3) — evening; late. */
    public static final ChineseCharacterEntry 晚_EVENING_LATE = entry("晚")
            .py(ZERO, U, Body.A, Tail.N, T3).strokes(11).radical(72)
            .leftRight(zi("日"), zi("免"))
            .phonoSemantic(zi("日"), zi("免"));

    public static final List<ChineseCharacterEntry> ALL = List.of(碗_BOWL, 晚_EVENING_LATE);
}
