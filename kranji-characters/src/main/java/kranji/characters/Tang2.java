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

/** Characters pronounced tang (tone 2). */
public final class Tang2 {
    private Tang2() {}

    /** 糖 (tang2) — sugar; candy. */
    public static final ChineseCharacterEntry 糖_SUGAR_CANDY = entry("糖")
            .py(T, OPEN, Body.A, Tail.NG, T2).strokes(16).radical(119)
            .leftRight(zi("米"), zi("唐"))
            .phonoSemantic(zi("米"), zi("唐"));

    /** 塘 (tang2) — pond; pool. */
    public static final ChineseCharacterEntry 塘_POND_POOL = entry("塘")
            .py(T, OPEN, Body.A, Tail.NG, T2).strokes(13).radical(32)
            .leftRight(zi("土"), zi("唐"))
            .phonoSemantic(zi("土"), zi("唐"));

    public static final List<ChineseCharacterEntry> ALL = List.of(糖_SUGAR_CANDY, 塘_POND_POOL);
}
