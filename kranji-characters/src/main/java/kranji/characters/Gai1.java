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

/** Characters pronounced gai (tone 1). */
public final class Gai1 {
    private Gai1() {}

    /** 该 (gai1) — should; that. */
    public static final ChineseCharacterEntry 该_SHOULD_THAT = entry("该")
            .py(G, OPEN, Body.A, Tail.VOWEL_I, T1).strokes(8).radical(149)
            .leftRight(YAN_ZI_PANG, zi("亥"))
            .phonoSemantic(YAN_ZI_PANG, zi("亥"));

    public static final List<ChineseCharacterEntry> ALL = List.of(该_SHOULD_THAT);
}
