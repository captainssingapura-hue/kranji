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

/** Characters pronounced mi (tone 4). */
public final class Mi4 {
    private Mi4() {}

    /** 秘 (mi4) — secret. */
    public static final ChineseCharacterEntry 秘_SECRET = entry("秘")
            .py(M, OPEN, Body.I, Tail.NONE, T4).strokes(10).radical(115)
            .leftRight(zi("禾"), zi("必"))
            .phonoSemantic(zi("禾"), zi("必"));

    /** 密 (mi4) — dense; secret. */
    public static final ChineseCharacterEntry 密_DENSE_SECRET = entry("密")
            .py(M, OPEN, Body.I, Tail.NONE, T4).strokes(11).radical(40)
            .topBottom(BAO_GAI_TOU, zi("必山"))
            .phonoSemantic(BAO_GAI_TOU, zi("必"));

    public static final List<ChineseCharacterEntry> ALL = List.of(秘_SECRET, 密_DENSE_SECRET);
}
