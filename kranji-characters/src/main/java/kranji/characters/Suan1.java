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

/** Characters pronounced suan (tone 1). */
public final class Suan1 {
    private Suan1() {}

    /** 酸 (suan1) — sour; acid. */
    public static final ChineseCharacterEntry 酸_SOUR_ACID = entry("酸")
            .py(S, U, Body.A, Tail.N, T1).strokes(14).radical(164)
            .leftRight(zi("酉"), zi("夋"))
            .phonoSemantic(zi("酉"), zi("夋"));

    public static final List<ChineseCharacterEntry> ALL = List.of(酸_SOUR_ACID);
}
