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

/** Characters pronounced jiu (tone 4). */
public final class Jiu4 {
    private Jiu4() {}

    /** 就 (jiu4) — then; at once. */
    public static final ChineseCharacterEntry 就_THEN_AT_ONCE = entry("就")
            .py(J, I, Body.O, Tail.VOWEL_U, T4).strokes(12).radical(43)
            .leftRight(zi("京"), zi("尤"))
            .phonoSemantic(zi("尤"), zi("京"));

    public static final List<ChineseCharacterEntry> ALL = List.of(就_THEN_AT_ONCE);
}
