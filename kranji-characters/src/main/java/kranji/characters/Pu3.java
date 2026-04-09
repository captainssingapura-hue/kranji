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

/** Characters pronounced pu (tone 3). */
public final class Pu3 {
    private Pu3() {}

    /** 普 (pu3) — universal. */
    public static final ChineseCharacterEntry 普_UNIVERSAL = entry("普")
            .py(P, U, Body.U, Tail.NONE, T3).strokes(12).radical(72)
            .topBottom(zi("并"), zi("日"))
            .phonoSemantic(zi("日"), zi("并"));

    public static final List<ChineseCharacterEntry> ALL = List.of(普_UNIVERSAL);
}
