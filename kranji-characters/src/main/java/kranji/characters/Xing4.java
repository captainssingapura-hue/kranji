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

/** Characters pronounced xing (tone 4). */
public final class Xing4 {
    private Xing4() {}

    /** 性 (xing4) — nature; gender. */
    public static final ChineseCharacterEntry 性_NATURE_GENDER = entry("性")
            .py(X, OPEN, Body.I, Tail.NG, T4).strokes(8).radical(61)
            .leftRight(SHU_XIN_PANG, zi("生"))
            .phonoSemantic(SHU_XIN_PANG, zi("生"));

    /** 幸 (xing4) — lucky; fortune. */
    public static final ChineseCharacterEntry 幸_LUCKY_FORTUNE = entry("幸")
            .py(X, OPEN, Body.I, Tail.NG, T4).strokes(8).radical(51)
            .singular()
            .compoundIndicative("lucky; fortune");

    public static final List<ChineseCharacterEntry> ALL = List.of(性_NATURE_GENDER, 幸_LUCKY_FORTUNE);
}
