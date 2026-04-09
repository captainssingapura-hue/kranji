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

/** Characters pronounced que (tone 1). */
public final class Que1 {
    private Que1() {}

    /** 缺 (que1) — lack; absent. */
    public static final ChineseCharacterEntry 缺_LACK_ABSENT = entry("缺")
            .py(Q, V, Body.E_CARON, Tail.NONE, T1).strokes(10).radical(121)
            .leftRight(zi("缶"), zi("夬"))
            .phonoSemantic(zi("缶"), zi("夬"));

    public static final List<ChineseCharacterEntry> ALL = List.of(缺_LACK_ABSENT);
}
