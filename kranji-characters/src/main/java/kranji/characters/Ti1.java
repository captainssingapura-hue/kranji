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

/** Characters pronounced ti (tone 1). */
public final class Ti1 {
    private Ti1() {}

    /** 踢 (ti1) — kick. */
    public static final ChineseCharacterEntry 踢_KICK = entry("踢")
            .py(T, OPEN, Body.I, Tail.NONE, T1).strokes(15).radical(157)
            .leftRight(zi("足"), zi("易"))
            .phonoSemantic(zi("足"), zi("易"));

    /** 梯 (ti1) — ladder; stairs. */
    public static final ChineseCharacterEntry 梯_LADDER_STAIRS = entry("梯")
            .py(T, OPEN, Body.I, Tail.NONE, T1).strokes(11).radical(75)
            .leftRight(zi("木"), zi("弟"))
            .phonoSemantic(zi("木"), zi("弟"));

    public static final List<ChineseCharacterEntry> ALL = List.of(踢_KICK, 梯_LADDER_STAIRS);
}
