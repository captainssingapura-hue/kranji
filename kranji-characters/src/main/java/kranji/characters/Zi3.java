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

/** Characters pronounced zi (tone 3). */
public final class Zi3 {
    private Zi3() {}

    /** 子 (zi3) — child; seed. */
    public static final ChineseCharacterEntry 子_CHILD_SEED = entry("子")
            .py(Z, OPEN, Body.NULL, Tail.NONE, T3).strokes(3).radical(39)
            .singular()
            .pictograph();

    /** 仔 (zi3) — careful; young. */
    public static final ChineseCharacterEntry 仔_CAREFUL_YOUNG = entry("仔")
            .py(Z, OPEN, Body.NULL, Tail.NONE, T3).strokes(5).radical(9)
            .leftRight(DAN_REN_PANG, zi("子"))
            .phonoSemantic(DAN_REN_PANG, zi("子"));

    public static final List<ChineseCharacterEntry> ALL = List.of(子_CHILD_SEED, 仔_CAREFUL_YOUNG);
}
