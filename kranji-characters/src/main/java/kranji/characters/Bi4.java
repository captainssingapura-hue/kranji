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

/** Characters pronounced bi (tone 4). */
public final class Bi4 {
    private Bi4() {}

    /** 必 (bi4) — must. */
    public static final ChineseCharacterEntry 必_MUST = entry("必")
            .py(B, OPEN, Body.I, Tail.NONE, T4).strokes(5).radical(61)
            .singular()
            .indicative("must");

    /** 闭 (bi4) — close; shut. */
    public static final ChineseCharacterEntry 闭_CLOSE_SHUT = entry("闭")
            .py(B, OPEN, Body.I, Tail.NONE, T4).strokes(6).radical(169)
            .semiEnclosureT3(zi("门"), zi("才"))
            .compoundIndicative("close; shut");

    /** 臂 (bi4) — arm. */
    public static final ChineseCharacterEntry 臂_ARM = entry("臂")
            .py(B, OPEN, Body.I, Tail.NONE, T4).strokes(17).radical(130)
            .topBottom(zi("辟"), zi("月"))
            .phonoSemantic(zi("月"), zi("辟"));

    public static final List<ChineseCharacterEntry> ALL = List.of(必_MUST, 闭_CLOSE_SHUT, 臂_ARM);
}
