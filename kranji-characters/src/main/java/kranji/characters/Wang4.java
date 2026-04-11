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

/** Characters pronounced wang (tone 4). */
public final class Wang4 {
    private Wang4() {}

    /** 望 (wang4) — hope; gaze. */
    public static final ChineseCharacterEntry 望_HOPE_GAZE = entry("望")
            .py(ZERO, U, Body.A, Tail.NG, T4).strokes(11).radical(74)
            .topBottom(zi("亡月"), zi("王"))
            .phonoSemantic(zi("月"), zi("亡"));

    /** 忘 (wang4) — forget. */
    public static final ChineseCharacterEntry 忘_FORGET = entry("忘")
            .py(ZERO, U, Body.A, Tail.NG, T4).strokes(7).radical(61)
            .topBottom(zi("亡"), zi("心"))
            .phonoSemantic(zi("心"), zi("亡"));

    public static final List<ChineseCharacterEntry> ALL = List.of(望_HOPE_GAZE, 忘_FORGET);
}
