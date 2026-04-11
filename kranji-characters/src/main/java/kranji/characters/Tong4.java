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

/** Characters pronounced tong (tone 4). */
public final class Tong4 {
    private Tong4() {}

    /** 痛 (tong4) — pain; ache. */
    public static final ChineseCharacterEntry 痛_PAIN_ACHE = entry("痛")
            .py(T, OPEN, Body.O, Tail.NG, T4).strokes(12).radical(104)
            .semiEnclosureUL(BING_ZI_PANG, zi("甬"))
            .phonoSemantic(BING_ZI_PANG, zi("甬"));

    public static final List<ChineseCharacterEntry> ALL = List.of(痛_PAIN_ACHE);
}
