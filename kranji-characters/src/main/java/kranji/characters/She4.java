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

/** Characters pronounced she (tone 4). */
public final class She4 {
    private She4() {}

    /** 社 (she4) — society. */
    public static final ChineseCharacterEntry 社_SOCIETY = entry("社")
            .py(SH, OPEN, Body.E, Tail.NONE, T4).strokes(7).radical(113)
            .leftRight(SHI_ZI_PANG_SPIRIT, zi("土"))
            .phonoSemantic(SHI_ZI_PANG_SPIRIT, zi("土"));

    /** 设 (she4) — set up; design. */
    public static final ChineseCharacterEntry 设_SET_UP_DESIGN = entry("设")
            .py(SH, OPEN, Body.E, Tail.NONE, T4).strokes(6).radical(149)
            .leftRight(YAN_ZI_PANG, zi("殳"))
            .phonoSemantic(YAN_ZI_PANG, zi("殳"));

    public static final List<ChineseCharacterEntry> ALL = List.of(社_SOCIETY, 设_SET_UP_DESIGN);
}
