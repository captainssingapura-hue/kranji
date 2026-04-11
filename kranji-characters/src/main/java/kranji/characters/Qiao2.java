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

/** Characters pronounced qiao (tone 2). */
public final class Qiao2 {
    private Qiao2() {}

    /** 桥 (qiao2) — bridge. */
    public static final ChineseCharacterEntry 桥_BRIDGE = entry("桥")
            .py(Q, I, Body.A, Tail.VOWEL_U, T2).strokes(10).radical(75)
            .leftRight(MU, zi("乔"))
            .phonoSemantic(MU, zi("乔"));

    public static final List<ChineseCharacterEntry> ALL = List.of(桥_BRIDGE);
}
