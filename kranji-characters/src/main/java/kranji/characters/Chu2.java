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

/** Characters pronounced chu (tone 2). */
public final class Chu2 {
    private Chu2() {}

    /** 除 (chu2) — remove; except. */
    public static final ChineseCharacterEntry 除_REMOVE_EXCEPT = entry("除")
            .py(CH, U, Body.U, Tail.NONE, T2).strokes(9).radical(170)
            .leftRight(ZUO_ER_PANG, zi("余"))
            .phonoSemantic(ZUO_ER_PANG, zi("余"));

    /** 厨 (chu2) — kitchen. */
    public static final ChineseCharacterEntry 厨_KITCHEN = entry("厨")
            .py(CH, U, Body.U, Tail.NONE, T2).strokes(12).radical(27)
            .semiEnclosureUL(zi("厂"), zi("豆寸"))
            .phonoSemantic(zi("厂"), zi("尌"));

    public static final List<ChineseCharacterEntry> ALL = List.of(除_REMOVE_EXCEPT, 厨_KITCHEN);
}
