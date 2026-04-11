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

/** Characters pronounced hu (tone 2). */
public final class Hu2 {
    private Hu2() {}

    /** 湖 (hu2) — lake. */
    public static final ChineseCharacterEntry 湖_LAKE = entry("湖")
            .py(H, U, Body.U, Tail.NONE, T2).strokes(12).radical(85)
            .leftRight(SAN_DIAN_SHUI, zi("胡"))
            .phonoSemantic(SAN_DIAN_SHUI, zi("胡"));

    /** 蝴 (hu2) — butterfly. */
    public static final ChineseCharacterEntry 蝴_BUTTERFLY = entry("蝴")
            .py(H, U, Body.U, Tail.NONE, T2).strokes(15).radical(142)
            .leftRight(zi("虫"), zi("胡"))
            .phonoSemantic(zi("虫"), zi("胡"));

    public static final List<ChineseCharacterEntry> ALL = List.of(湖_LAKE, 蝴_BUTTERFLY);
}
