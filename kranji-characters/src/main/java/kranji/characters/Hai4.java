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

/** Characters pronounced hai (tone 4). */
public final class Hai4 {
    private Hai4() {}

    /** 害 (hai4) — harm; afraid. */
    public static final ChineseCharacterEntry 害_HARM_AFRAID = entry("害")
            .py(H, OPEN, Body.A, Tail.VOWEL_I, T4).strokes(10).radical(40)
            .topBottom(BAO_GAI_TOU, zi("丰口"))
            .compoundIndicative("harm; afraid");

    public static final List<ChineseCharacterEntry> ALL = List.of(害_HARM_AFRAID);
}
