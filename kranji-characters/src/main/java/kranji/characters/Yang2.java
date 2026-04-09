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

/** Characters pronounced yang (tone 2). */
public final class Yang2 {
    private Yang2() {}

    /** 阳 (yang2) — sun; positive. */
    public static final ChineseCharacterEntry 阳_SUN_POSITIVE = entry("阳")
            .py(ZERO, I, Body.A, Tail.NG, T2).strokes(6).radical(170)
            .leftRight(zi("阝"), zi("日"))
            .phonoSemantic(zi("阝"), zi("日"));

    /** 羊 (yang2) — sheep; goat. */
    public static final ChineseCharacterEntry 羊_SHEEP_GOAT = entry("羊")
            .py(ZERO, I, Body.A, Tail.NG, T2).strokes(6).radical(123)
            .singular()
            .pictograph();

    /** 洋 (yang2) — ocean; foreign. */
    public static final ChineseCharacterEntry 洋_OCEAN_FOREIGN = entry("洋")
            .py(ZERO, I, Body.A, Tail.NG, T2).strokes(9).radical(85)
            .leftRight(SAN_DIAN_SHUI, zi("羊"))
            .phonoSemantic(SAN_DIAN_SHUI, zi("羊"));

    public static final List<ChineseCharacterEntry> ALL = List.of(阳_SUN_POSITIVE, 羊_SHEEP_GOAT, 洋_OCEAN_FOREIGN);
}
