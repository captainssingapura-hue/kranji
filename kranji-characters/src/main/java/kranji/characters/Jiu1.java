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

/** Characters pronounced jiu (tone 1). */
public final class Jiu1 {
    private Jiu1() {}

    /** 究 (jiu1) — investigate. */
    public static final ChineseCharacterEntry 究_INVESTIGATE = entry("究")
            .py(J, I, Body.O, Tail.VOWEL_U, T1).strokes(7).radical(116)
            .topBottom(zi("穴"), zi("九"))
            .phonoSemantic(zi("穴"), zi("九"));

    public static final List<ChineseCharacterEntry> ALL = List.of(究_INVESTIGATE);
}
