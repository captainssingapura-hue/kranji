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

/** Characters pronounced xu (tone 4). */
public final class Xu4 {
    private Xu4() {}

    /** 续 (xu4) — continue. */
    public static final ChineseCharacterEntry 续_CONTINUE = entry("续")
            .py(X, V, Body.V, Tail.NONE, T4).strokes(11).radical(120)
            .leftRight(JIAO_SI_PANG, zi("卖"))
            .phonoSemantic(JIAO_SI_PANG, zi("卖"));

    public static final List<ChineseCharacterEntry> ALL = List.of(续_CONTINUE);
}
