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

/** Characters pronounced jun (tone 1). */
public final class Jun1 {
    private Jun1() {}

    /** 军 (jun1) — army. */
    public static final ChineseCharacterEntry 军_ARMY = entry("军")
            .py(J, V, Body.E, Tail.N, T1).strokes(6).radical(14)
            .topBottom(TU_BAO_GAI, zi("车"))
            .compoundIndicative("army");

    /** 均 (jun1) — equal; even. */
    public static final ChineseCharacterEntry 均_EQUAL_EVEN = entry("均")
            .py(J, V, Body.E, Tail.N, T1).strokes(7).radical(32)
            .leftRight(zi("土"), zi("匀"))
            .phonoSemantic(zi("土"), zi("匀"));

    public static final List<ChineseCharacterEntry> ALL = List.of(军_ARMY, 均_EQUAL_EVEN);
}
