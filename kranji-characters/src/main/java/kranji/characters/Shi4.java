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

/** Characters pronounced shi (tone 4). */
public final class Shi4 {
    private Shi4() {}

    /** 是 (shi4) — be; correct. */
    public static final ChineseCharacterEntry 是_BE_CORRECT = entry("是")
            .py(SH, OPEN, Body.NULL, Tail.NONE, T4).strokes(9).radical(72)
            .topBottom(zi("日"), zi("疋"))
            .compoundIndicative("be; correct");

    /** 事 (shi4) — matter; thing. */
    public static final ChineseCharacterEntry 事_MATTER_THING = entry("事")
            .py(SH, OPEN, Body.NULL, Tail.NONE, T4).strokes(8).radical(6)
            .singular()
            .pictograph();

    /** 市 (shi4) — city; market. */
    public static final ChineseCharacterEntry 市_CITY_MARKET = entry("市")
            .py(SH, OPEN, Body.NULL, Tail.NONE, T4).strokes(5).radical(50)
            .topBottom(WEN_ZI_TOU, zi("巾"))
            .compoundIndicative("city; market");

    /** 视 (shi4) — look; regard. */
    public static final ChineseCharacterEntry 视_LOOK_REGARD = entry("视")
            .py(SH, OPEN, Body.NULL, Tail.NONE, T4).strokes(8).radical(147)
            .leftRight(SHI_ZI_PANG_SPIRIT, zi("见"))
            .phonoSemantic(SHI_ZI_PANG_SPIRIT, zi("见"));

    /** 示 (shi4) — show; indicate. */
    public static final ChineseCharacterEntry 示_SHOW_INDICATE = entry("示")
            .py(SH, OPEN, Body.NULL, Tail.NONE, T4).strokes(5).radical(113)
            .singular()
            .pictograph();

    /** 适 (shi4) — suitable; fit. */
    public static final ChineseCharacterEntry 适_SUITABLE_FIT = entry("适")
            .py(SH, OPEN, Body.NULL, Tail.NONE, T4).strokes(9).radical(162)
            .semiEnclosureBL(ZOU_ZHI_DI, zi("括"))
            .phonoSemantic(ZOU_ZHI_DI, zi("括"));

    /** 室 (shi4) — room. */
    public static final ChineseCharacterEntry 室_ROOM = entry("室")
            .py(SH, OPEN, Body.NULL, Tail.NONE, T4).strokes(9).radical(40)
            .topBottom(BAO_GAI_TOU, zi("至"))
            .phonoSemantic(BAO_GAI_TOU, zi("至"));

    /** 试 (shi4) — try; test. */
    public static final ChineseCharacterEntry 试_TRY_TEST = entry("试")
            .py(SH, OPEN, Body.NULL, Tail.NONE, T4).strokes(8).radical(149)
            .leftRight(YAN_ZI_PANG, zi("式"))
            .phonoSemantic(YAN_ZI_PANG, zi("式"));

    /** 释 (shi4) — release; explain. */
    public static final ChineseCharacterEntry 释_RELEASE_EXPLAIN = entry("释")
            .py(SH, OPEN, Body.NULL, Tail.NONE, T4).strokes(12).radical(165)
            .leftRight(zi("釆"), zi("睪"))
            .phonoSemantic(zi("釆"), zi("睪"));

    /** 势 (shi4) — power; trend. */
    public static final ChineseCharacterEntry 势_POWER_TREND = entry("势")
            .py(SH, OPEN, Body.NULL, Tail.NONE, T4).strokes(8).radical(19)
            .topBottom(zi("执"), zi("力"))
            .phonoSemantic(zi("力"), zi("执"));

    public static final List<ChineseCharacterEntry> ALL = List.of(是_BE_CORRECT, 事_MATTER_THING, 市_CITY_MARKET, 视_LOOK_REGARD, 示_SHOW_INDICATE, 适_SUITABLE_FIT, 室_ROOM, 试_TRY_TEST, 释_RELEASE_EXPLAIN, 势_POWER_TREND);
}
