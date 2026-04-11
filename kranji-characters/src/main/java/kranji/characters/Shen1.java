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

/** Characters pronounced shen (tone 1). */
public final class Shen1 {
    private Shen1() {}

    /** 身 (shen1) — body. */
    public static final ChineseCharacterEntry 身_BODY = entry("身")
            .py(SH, OPEN, Body.E, Tail.N, T1).strokes(7).radical(158)
            .singular()
            .pictograph();

    /** 深 (shen1) — deep. */
    public static final ChineseCharacterEntry 深_DEEP = entry("深")
            .py(SH, OPEN, Body.E, Tail.N, T1).strokes(11).radical(85)
            .leftRight(SAN_DIAN_SHUI, zi("罙"))
            .phonoSemantic(SAN_DIAN_SHUI, zi("罙"));

    /** 申 (shen1) — state; apply. */
    public static final ChineseCharacterEntry 申_STATE_APPLY = entry("申")
            .py(SH, OPEN, Body.E, Tail.N, T1).strokes(5).radical(102)
            .singular()
            .pictograph();

    public static final List<ChineseCharacterEntry> ALL = List.of(身_BODY, 深_DEEP, 申_STATE_APPLY);
}
