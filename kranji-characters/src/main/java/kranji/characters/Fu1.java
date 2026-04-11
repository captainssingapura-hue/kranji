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

/** Characters pronounced fu (tone 1). */
public final class Fu1 {
    private Fu1() {}

    /** 夫 (fu1) — man; husband. */
    public static final ChineseCharacterEntry 夫_MAN_HUSBAND = entry("夫")
            .py(F, U, Body.U, Tail.NONE, T1).strokes(4).radical(37)
            .singular()
            .indicative("man; husband");

    /** 肤 (fu1) — skin. */
    public static final ChineseCharacterEntry 肤_SKIN = entry("肤")
            .py(F, U, Body.U, Tail.NONE, T1).strokes(8).radical(130)
            .leftRight(zi("月"), zi("夫"))
            .phonoSemantic(zi("月"), zi("夫"));

    public static final List<ChineseCharacterEntry> ALL = List.of(夫_MAN_HUSBAND, 肤_SKIN);
}
