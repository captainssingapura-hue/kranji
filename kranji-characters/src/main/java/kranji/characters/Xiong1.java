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

/** Characters pronounced xiong (tone 1). */
public final class Xiong1 {
    private Xiong1() {}

    /** 胸 (xiong1) — chest; breast. */
    public static final ChineseCharacterEntry 胸_CHEST_BREAST = entry("胸")
            .py(X, I, Body.O, Tail.NG, T1).strokes(10).radical(130)
            .leftRight(zi("月"), zi("匈"))
            .phonoSemantic(zi("月"), zi("匈"));

    public static final List<ChineseCharacterEntry> ALL = List.of(胸_CHEST_BREAST);
}
