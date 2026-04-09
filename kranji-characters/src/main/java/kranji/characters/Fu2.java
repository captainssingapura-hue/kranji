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

/** Characters pronounced fu (tone 2). */
public final class Fu2 {
    private Fu2() {}

    /** 服 (fu2) — clothes; serve. */
    public static final ChineseCharacterEntry 服_CLOTHES_SERVE = entry("服")
            .py(F, U, Body.U, Tail.NONE, T2).strokes(8).radical(74)
            .leftRight(zi("月"), zi("服"))
            .phonoSemantic(zi("月"), zi("服"));

    /** 符 (fu2) — symbol; tally. */
    public static final ChineseCharacterEntry 符_SYMBOL_TALLY = entry("符")
            .py(F, U, Body.U, Tail.NONE, T2).strokes(11).radical(118)
            .topBottom(ZHU_ZI_TOU, zi("付"))
            .phonoSemantic(ZHU_ZI_TOU, zi("付"));

    /** 幅 (fu2) — width; scroll. */
    public static final ChineseCharacterEntry 幅_WIDTH_SCROLL = entry("幅")
            .py(F, U, Body.U, Tail.NONE, T2).strokes(12).radical(50)
            .leftRight(zi("巾"), zi("畐"))
            .phonoSemantic(zi("巾"), zi("畐"));

    public static final List<ChineseCharacterEntry> ALL = List.of(服_CLOTHES_SERVE, 符_SYMBOL_TALLY, 幅_WIDTH_SCROLL);
}
