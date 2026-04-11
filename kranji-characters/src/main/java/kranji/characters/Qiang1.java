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

/** Characters pronounced qiang (tone 1). */
public final class Qiang1 {
    private Qiang1() {}

    /** 枪 (qiang1) — gun; spear. */
    public static final ChineseCharacterEntry 枪_GUN_SPEAR = entry("枪")
            .py(Q, I, Body.A, Tail.NG, T1).strokes(8).radical(75)
            .leftRight(MU, zi("仓"))
            .phonoSemantic(MU, zi("仓"));

    public static final List<ChineseCharacterEntry> ALL = List.of(枪_GUN_SPEAR);
}
