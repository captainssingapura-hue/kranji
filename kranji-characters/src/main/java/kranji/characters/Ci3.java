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

/** Characters pronounced ci (tone 3). */
public final class Ci3 {
    private Ci3() {}

    /** 此 (ci3) — this; here. */
    public static final ChineseCharacterEntry 此_THIS_HERE = entry("此")
            .py(C, OPEN, Body.NULL, Tail.NONE, T3).strokes(6).radical(77)
            .leftRight(zi("止"), zi("匕"))
            .compoundIndicative("this; here");

    public static final List<ChineseCharacterEntry> ALL = List.of(此_THIS_HERE);
}
