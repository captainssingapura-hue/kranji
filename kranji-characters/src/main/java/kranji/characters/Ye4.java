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

/** Characters pronounced ye (tone 4). */
public final class Ye4 {
    private Ye4() {}

    /** 业 (ye4) — profession. */
    public static final ChineseCharacterEntry 业_PROFESSION = entry("业")
            .py(ZERO, I, Body.E_CARON, Tail.NONE, T4).strokes(5).radical(1)
            .singular()
            .pictograph();

    /** 夜 (ye4) — night. */
    public static final ChineseCharacterEntry 夜_NIGHT = entry("夜")
            .py(ZERO, I, Body.E_CARON, Tail.NONE, T4).strokes(8).radical(36)
            .topBottom(WEN_ZI_TOU, zi("亻夂"))
            .compoundIndicative("night");

    /** 叶 (ye4) — leaf. */
    public static final ChineseCharacterEntry 叶_LEAF = entry("叶")
            .py(ZERO, I, Body.E_CARON, Tail.NONE, T4).strokes(5).radical(30)
            .leftRight(zi("口"), zi("十"))
            .phonoSemantic(zi("口"), zi("十"));

    public static final List<ChineseCharacterEntry> ALL = List.of(业_PROFESSION, 夜_NIGHT, 叶_LEAF);
}
