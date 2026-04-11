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

/** Characters pronounced chang (tone 3). */
public final class Chang3 {
    private Chang3() {}

    /** 场 (chang3) — field; place. */
    public static final ChineseCharacterEntry 场_FIELD_PLACE = entry("场")
            .py(CH, OPEN, Body.A, Tail.NG, T3).strokes(6).radical(32)
            .leftRight(zi("土"), zi("昜"))
            .phonoSemantic(zi("土"), zi("昜"));

    public static final List<ChineseCharacterEntry> ALL = List.of(场_FIELD_PLACE);
}
