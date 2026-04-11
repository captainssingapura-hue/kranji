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

/** Characters pronounced huo (tone 2). */
public final class Huo2 {
    private Huo2() {}

    /** 活 (huo2) — live; alive. */
    public static final ChineseCharacterEntry 活_LIVE_ALIVE = entry("活")
            .py(H, U, Body.O, Tail.NONE, T2).strokes(9).radical(85)
            .leftRight(SAN_DIAN_SHUI, zi("舌"))
            .phonoSemantic(SAN_DIAN_SHUI, zi("舌"));

    public static final List<ChineseCharacterEntry> ALL = List.of(活_LIVE_ALIVE);
}
