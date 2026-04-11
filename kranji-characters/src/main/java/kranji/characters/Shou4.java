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

/** Characters pronounced shou (tone 4). */
public final class Shou4 {
    private Shou4() {}

    /** 受 (shou4) — receive. */
    public static final ChineseCharacterEntry 受_RECEIVE = entry("受")
            .py(SH, OPEN, Body.O, Tail.VOWEL_U, T4).strokes(8).radical(29)
            .topBottom(zi("爫"), zi("又"))
            .compoundIndicative("receive");

    /** 瘦 (shou4) — thin; lean. */
    public static final ChineseCharacterEntry 瘦_THIN_LEAN = entry("瘦")
            .py(SH, OPEN, Body.O, Tail.VOWEL_U, T4).strokes(14).radical(104)
            .semiEnclosureUL(BING_ZI_PANG, zi("叟"))
            .phonoSemantic(BING_ZI_PANG, zi("叟"));

    /** 授 (shou4) — grant; teach. */
    public static final ChineseCharacterEntry 授_GRANT_TEACH = entry("授")
            .py(SH, OPEN, Body.O, Tail.VOWEL_U, T4).strokes(11).radical(64)
            .leftRight(TI_SHOU_PANG, zi("受"))
            .phonoSemantic(TI_SHOU_PANG, zi("受"));

    /** 寿 (shou4) — longevity. */
    public static final ChineseCharacterEntry 寿_LONGEVITY = entry("寿")
            .py(SH, OPEN, Body.O, Tail.VOWEL_U, T4).strokes(7).radical(41)
            .singular()
            .compoundIndicative("longevity");

    public static final List<ChineseCharacterEntry> ALL = List.of(受_RECEIVE, 瘦_THIN_LEAN, 授_GRANT_TEACH, 寿_LONGEVITY);
}
