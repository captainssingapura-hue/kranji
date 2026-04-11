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

/** Characters pronounced lu (tone 4). */
public final class Lu4 {
    private Lu4() {}

    /** 路 (lu4) — road; path. */
    public static final ChineseCharacterEntry 路_ROAD_PATH = entry("路")
            .py(L, U, Body.U, Tail.NONE, T4).strokes(13).radical(157)
            .leftRight(zi("足"), zi("各"))
            .phonoSemantic(zi("足"), zi("各"));

    /** 陆 (lu4) — land; six. */
    public static final ChineseCharacterEntry 陆_LAND_SIX = entry("陆")
            .py(L, U, Body.U, Tail.NONE, T4).strokes(7).radical(170)
            .leftRight(ZUO_ER_PANG, zi("击"))
            .phonoSemantic(ZUO_ER_PANG, zi("击"));

    public static final List<ChineseCharacterEntry> ALL = List.of(路_ROAD_PATH, 陆_LAND_SIX);
}
