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

/** Characters pronounced hui (tone 4). */
public final class Hui4 {
    private Hui4() {}

    /** 会 (hui4) — meet; can. */
    public static final ChineseCharacterEntry 会_MEET_CAN = entry("会")
            .py(H, U, Body.E, Tail.VOWEL_I, T4).strokes(6).radical(9)
            .topBottom(zi("人"), zi("云"))
            .compoundIndicative("meet; can");

    public static final List<ChineseCharacterEntry> ALL = List.of(会_MEET_CAN);
}
