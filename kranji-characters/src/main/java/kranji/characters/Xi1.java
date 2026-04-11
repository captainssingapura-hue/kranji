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

/** Characters pronounced xi (tone 1). */
public final class Xi1 {
    private Xi1() {}

    /** 西 (xi1) — west. */
    public static final ChineseCharacterEntry 西_WEST = entry("西")
            .py(X, OPEN, Body.I, Tail.NONE, T1).strokes(6).radical(146)
            .singular()
            .pictograph();

    /** 息 (xi1) — rest; breath. */
    public static final ChineseCharacterEntry 息_REST_BREATH = entry("息")
            .py(X, OPEN, Body.I, Tail.NONE, T1).strokes(10).radical(61)
            .topBottom(zi("自"), zi("心"))
            .phonoSemantic(zi("心"), zi("自"));

    /** 惜 (xi1) — pity; cherish. */
    public static final ChineseCharacterEntry 惜_PITY_CHERISH = entry("惜")
            .py(X, OPEN, Body.I, Tail.NONE, T1).strokes(11).radical(61)
            .leftRight(SHU_XIN_PANG, zi("昔"))
            .phonoSemantic(SHU_XIN_PANG, zi("昔"));

    /** 吸 (xi1) — breathe; suck. */
    public static final ChineseCharacterEntry 吸_BREATHE_SUCK = entry("吸")
            .py(X, OPEN, Body.I, Tail.NONE, T1).strokes(6).radical(30)
            .leftRight(zi("口"), zi("及"))
            .phonoSemantic(zi("口"), zi("及"));

    /** 膝 (xi1) — knee. */
    public static final ChineseCharacterEntry 膝_KNEE = entry("膝")
            .py(X, OPEN, Body.I, Tail.NONE, T1).strokes(15).radical(130)
            .leftRight(zi("月"), zi("桼"))
            .phonoSemantic(zi("月"), zi("桼"));

    public static final List<ChineseCharacterEntry> ALL = List.of(西_WEST, 息_REST_BREATH, 惜_PITY_CHERISH, 吸_BREATHE_SUCK, 膝_KNEE);
}
