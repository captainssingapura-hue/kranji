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

/** Characters pronounced dan (tone 4). */
public final class Dan4 {
    private Dan4() {}

    /** 但 (dan4) — but; however. */
    public static final ChineseCharacterEntry 但_BUT_HOWEVER = entry("但")
            .py(D, OPEN, Body.A, Tail.N, T4).strokes(7).radical(9)
            .leftRight(DAN_REN_PANG, zi("旦"))
            .phonoSemantic(DAN_REN_PANG, zi("旦"));

    /** 蛋 (dan4) — egg. */
    public static final ChineseCharacterEntry 蛋_EGG = entry("蛋")
            .py(D, OPEN, Body.A, Tail.N, T4).strokes(11).radical(142)
            .topBottom(zi("疋"), zi("虫"))
            .phonoSemantic(zi("虫"), zi("疋"));

    public static final List<ChineseCharacterEntry> ALL = List.of(但_BUT_HOWEVER, 蛋_EGG);
}
