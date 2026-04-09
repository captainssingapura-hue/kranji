package kranji.characters;

import kranji.entry.ChineseCharacterEntry;

import java.util.List;

import static kranji.characters.Comp.*;
import static kranji.characters.EntryBuilder.entry;
import static kranji.component.Parts.*;
import static kranji.pinyin.Initial.*;
import static kranji.pinyin.Head.*;
import kranji.pinyin.Body;
import kranji.pinyin.Tail;

/** Characters pronounced xian (tone 4). */
public final class Xian4 {
    private Xian4() {}

    /** 现 (xian4) — appear; now. */
    public static final ChineseCharacterEntry 现_APPEAR_NOW = entry("现")
            .py(X, I, Body.A, Tail.N, T4).strokes(8).radical(96)
            .leftRight(zi("王"), zi("见"))
            .phonoSemantic(zi("王"), zi("见"));

    /** 县 (xian4) — county. */
    public static final ChineseCharacterEntry 县_COUNTY = entry("县")
            .py(X, I, Body.A, Tail.N, T4).strokes(7).radical(109)
            .topBottom(zi("且"), zi("厶"))
            .compoundIndicative("county");

    /** 限 (xian4) — limit; restrict. */
    public static final ChineseCharacterEntry 限_LIMIT_RESTRICT = entry("限")
            .py(X, I, Body.A, Tail.N, T4).strokes(8).radical(170)
            .leftRight(zi("阝"), zi("艮"))
            .phonoSemantic(zi("阝"), zi("艮"));

    /** 陷 (xian4) — trap; fall into. */
    public static final ChineseCharacterEntry 陷_TRAP_FALL_INTO = entry("陷")
            .py(X, I, Body.A, Tail.N, T4).strokes(10).radical(170)
            .leftRight(zi("阝"), zi("臽"))
            .phonoSemantic(zi("阝"), zi("臽"));

    /** 献 (xian4) — offer; dedicate. */
    public static final ChineseCharacterEntry 献_OFFER_DEDICATE = entry("献")
            .py(X, I, Body.A, Tail.N, T4).strokes(13).radical(94)
            .leftRight(zi("南"), zi("犬"))
            .compoundIndicative("offer; dedicate");

    public static final List<ChineseCharacterEntry> ALL = List.of(现_APPEAR_NOW, 县_COUNTY, 限_LIMIT_RESTRICT, 陷_TRAP_FALL_INTO, 献_OFFER_DEDICATE);
}
