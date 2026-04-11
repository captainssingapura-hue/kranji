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

/** Characters pronounced you (tone 1). */
public final class You1 {
    private You1() {}

    /** 优 (you1) — excellent. */
    public static final ChineseCharacterEntry 优_EXCELLENT = entry("优")
            .py(ZERO, I, Body.O, Tail.VOWEL_U, T1).strokes(6).radical(9)
            .leftRight(DAN_REN_PANG, zi("尤"))
            .phonoSemantic(DAN_REN_PANG, zi("尤"));

    /** 忧 (you1) — worry; grief. */
    public static final ChineseCharacterEntry 忧_WORRY_GRIEF = entry("忧")
            .py(ZERO, I, Body.O, Tail.VOWEL_U, T1).strokes(7).radical(61)
            .leftRight(SHU_XIN_PANG, zi("尤"))
            .phonoSemantic(SHU_XIN_PANG, zi("尤"));

    public static final List<ChineseCharacterEntry> ALL = List.of(优_EXCELLENT, 忧_WORRY_GRIEF);
}
