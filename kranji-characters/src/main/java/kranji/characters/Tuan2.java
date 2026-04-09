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

/** Characters pronounced tuan (tone 2). */
public final class Tuan2 {
    private Tuan2() {}

    /** 团 (tuan2) — group; ball. */
    public static final ChineseCharacterEntry 团_GROUP_BALL = entry("团")
            .py(T, U, Body.A, Tail.N, T2).strokes(6).radical(31)
            .fullEnclosure(GUO_ZI_KUANG, zi("才"))
            .phonoSemantic(GUO_ZI_KUANG, zi("才"));

    public static final List<ChineseCharacterEntry> ALL = List.of(团_GROUP_BALL);
}
