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

/** Characters pronounced jiang (tone 4). */
public final class Jiang4 {
    private Jiang4() {}

    /** 酱 (jiang4) — sauce; paste. */
    public static final ChineseCharacterEntry 酱_SAUCE_PASTE = entry("酱")
            .py(J, I, Body.A, Tail.NG, T4).strokes(13).radical(164)
            .topBottom(zi("将"), zi("酉"))
            .phonoSemantic(zi("酉"), zi("将"));

    /** 降 (jiang4) — descend; drop. */
    public static final ChineseCharacterEntry 降_DESCEND_DROP = entry("降")
            .py(J, I, Body.A, Tail.NG, T4).strokes(8).radical(170)
            .leftRight(ZUO_ER_PANG, zi("夅"))
            .compoundIndicative("descend; drop");

    public static final List<ChineseCharacterEntry> ALL = List.of(酱_SAUCE_PASTE, 降_DESCEND_DROP);
}
