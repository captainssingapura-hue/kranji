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

/** Characters pronounced yan (tone 2). */
public final class Yan2 {
    private Yan2() {}

    /** 研 (yan2) — research. */
    public static final ChineseCharacterEntry 研_RESEARCH = entry("研")
            .py(ZERO, I, Body.A, Tail.N, T2).strokes(9).radical(112)
            .leftRight(zi("石"), zi("开"))
            .phonoSemantic(zi("石"), zi("开"));

    /** 言 (yan2) — speech; word. */
    public static final ChineseCharacterEntry 言_SPEECH_WORD = entry("言")
            .py(ZERO, I, Body.A, Tail.N, T2).strokes(7).radical(149)
            .singular()
            .pictograph();

    /** 盐 (yan2) — salt. */
    public static final ChineseCharacterEntry 盐_SALT = entry("盐")
            .py(ZERO, I, Body.A, Tail.N, T2).strokes(10).radical(108)
            .topBottom(zi("卤"), zi("皿"))
            .compoundIndicative("salt");

    /** 严 (yan2) — strict; severe. */
    public static final ChineseCharacterEntry 严_STRICT_SEVERE = entry("严")
            .py(ZERO, I, Body.A, Tail.N, T2).strokes(7).radical(1)
            .singular()
            .compoundIndicative("strict; severe");

    /** 延 (yan2) — extend; delay. */
    public static final ChineseCharacterEntry 延_EXTEND_DELAY = entry("延")
            .py(ZERO, I, Body.A, Tail.N, T2).strokes(7).radical(54)
            .semiEnclosureBL(JIAN_ZHI_PANG, zi("丹"))
            .phonoSemantic(JIAN_ZHI_PANG, zi("丹"));

    public static final List<ChineseCharacterEntry> ALL = List.of(研_RESEARCH, 言_SPEECH_WORD, 盐_SALT, 严_STRICT_SEVERE, 延_EXTEND_DELAY);
}
