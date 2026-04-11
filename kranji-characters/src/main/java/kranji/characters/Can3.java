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

/** Characters pronounced can (tone 3). */
public final class Can3 {
    private Can3() {}

    /** 惨 (can3) — miserable. */
    public static final ChineseCharacterEntry 惨_MISERABLE = entry("惨")
            .py(C, OPEN, Body.A, Tail.N, T3).strokes(11).radical(61)
            .leftRight(SHU_XIN_PANG, zi("参"))
            .phonoSemantic(SHU_XIN_PANG, zi("参"));

    public static final List<ChineseCharacterEntry> ALL = List.of(惨_MISERABLE);
}
