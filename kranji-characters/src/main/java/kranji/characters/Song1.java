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

/** Characters pronounced song (tone 1). */
public final class Song1 {
    private Song1() {}

    /** 松 (song1) — pine; loose. */
    public static final ChineseCharacterEntry 松_PINE_LOOSE = entry("松")
            .py(S, OPEN, Body.O, Tail.NG, T1).strokes(8).radical(75)
            .leftRight(zi("木"), zi("公"))
            .phonoSemantic(zi("木"), zi("公"));

    public static final List<ChineseCharacterEntry> ALL = List.of(松_PINE_LOOSE);
}
