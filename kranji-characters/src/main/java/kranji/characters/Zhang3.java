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

/** Characters pronounced zhang (tone 3). */
public final class Zhang3 {
    private Zhang3() {}

    /** 掌 (zhang3) — palm; control. */
    public static final ChineseCharacterEntry 掌_PALM_CONTROL = entry("掌")
            .py(ZH, OPEN, Body.A, Tail.NG, T3).strokes(12).radical(64)
            .topBottom(zi("尚"), zi("手"))
            .phonoSemantic(zi("手"), zi("尚"));

    public static final List<ChineseCharacterEntry> ALL = List.of(掌_PALM_CONTROL);
}
