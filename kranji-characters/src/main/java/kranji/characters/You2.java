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

/** Characters pronounced you (tone 2). */
public final class You2 {
    private You2() {}

    /** 由 (you2) — from; by. */
    public static final ChineseCharacterEntry 由_FROM_BY = entry("由")
            .py(ZERO, I, Body.O, Tail.VOWEL_U, T2).strokes(5).radical(102)
            .singular()
            .pictograph();

    /** 邮 (you2) — mail; post. */
    public static final ChineseCharacterEntry 邮_MAIL_POST = entry("邮")
            .py(ZERO, I, Body.O, Tail.VOWEL_U, T2).strokes(7).radical(163)
            .leftRight(zi("由"), zi("阝"))
            .phonoSemantic(zi("阝"), zi("由"));

    /** 游 (you2) — swim; travel. */
    public static final ChineseCharacterEntry 游_SWIM_TRAVEL = entry("游")
            .py(ZERO, I, Body.O, Tail.VOWEL_U, T2).strokes(12).radical(85)
            .leftRight(SAN_DIAN_SHUI, zi("斿"))
            .phonoSemantic(SAN_DIAN_SHUI, zi("斿"));

    /** 油 (you2) — oil. */
    public static final ChineseCharacterEntry 油_OIL = entry("油")
            .py(ZERO, I, Body.O, Tail.VOWEL_U, T2).strokes(8).radical(85)
            .leftRight(SAN_DIAN_SHUI, zi("由"))
            .phonoSemantic(SAN_DIAN_SHUI, zi("由"));

    public static final List<ChineseCharacterEntry> ALL = List.of(由_FROM_BY, 邮_MAIL_POST, 游_SWIM_TRAVEL, 油_OIL);
}
