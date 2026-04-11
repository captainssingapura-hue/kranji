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

/** Characters pronounced wu (tone 1). */
public final class Wu1 {
    private Wu1() {}

    /** 污 (wu1) — dirty; pollute. */
    public static final ChineseCharacterEntry 污_DIRTY_POLLUTE = entry("污")
            .py(ZERO, U, Body.U, Tail.NONE, T1).strokes(6).radical(85)
            .leftRight(SAN_DIAN_SHUI, zi("亏"))
            .phonoSemantic(SAN_DIAN_SHUI, zi("亏"));

    public static final List<ChineseCharacterEntry> ALL = List.of(污_DIRTY_POLLUTE);
}
