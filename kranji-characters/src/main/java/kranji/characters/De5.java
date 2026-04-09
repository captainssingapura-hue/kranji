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

/** Characters pronounced de (tone 5). */
public final class De5 {
    private De5() {}

    /** 的 (de5) — target; of. */
    public static final ChineseCharacterEntry 的_TARGET_OF = entry("的")
            .py(D, OPEN, Body.E, Tail.NONE, T5).strokes(8).radical(106)
            .leftRight(zi("白"), zi("勺"))
            .phonoSemantic(zi("白"), zi("勺"));

    public static final List<ChineseCharacterEntry> ALL = List.of(的_TARGET_OF);
}
