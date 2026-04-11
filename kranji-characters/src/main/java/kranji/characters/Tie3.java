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

/** Characters pronounced tie (tone 3). */
public final class Tie3 {
    private Tie3() {}

    /** 铁 (tie3) — iron. */
    public static final ChineseCharacterEntry 铁_IRON = entry("铁")
            .py(T, I, Body.E_CARON, Tail.NONE, T3).strokes(10).radical(167)
            .leftRight(JIN_ZI_PANG, zi("失"))
            .phonoSemantic(JIN_ZI_PANG, zi("失"));

    public static final List<ChineseCharacterEntry> ALL = List.of(铁_IRON);
}
