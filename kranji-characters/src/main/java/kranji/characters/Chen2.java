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

/** Characters pronounced chen (tone 2). */
public final class Chen2 {
    private Chen2() {}

    /** 陈 (chen2) — display; stale. */
    public static final ChineseCharacterEntry 陈_DISPLAY_STALE = entry("陈")
            .py(CH, OPEN, Body.E, Tail.N, T2).strokes(7).radical(170)
            .leftRight(zi("阝"), zi("东"))
            .phonoSemantic(zi("阝"), zi("东"));

    /** 晨 (chen2) — morning; dawn. */
    public static final ChineseCharacterEntry 晨_MORNING_DAWN = entry("晨")
            .py(CH, OPEN, Body.E, Tail.N, T2).strokes(11).radical(72)
            .topBottom(zi("日"), zi("辰"))
            .phonoSemantic(zi("日"), zi("辰"));

    /** 沉 (chen2) — sink; deep. */
    public static final ChineseCharacterEntry 沉_SINK_DEEP = entry("沉")
            .py(CH, OPEN, Body.E, Tail.N, T2).strokes(7).radical(85)
            .leftRight(SAN_DIAN_SHUI, zi("冘"))
            .phonoSemantic(SAN_DIAN_SHUI, zi("冘"));

    public static final List<ChineseCharacterEntry> ALL = List.of(陈_DISPLAY_STALE, 晨_MORNING_DAWN, 沉_SINK_DEEP);
}
