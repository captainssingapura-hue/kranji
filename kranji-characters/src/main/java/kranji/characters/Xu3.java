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

/** Characters pronounced xu (tone 3). */
public final class Xu3 {
    private Xu3() {}

    /** 许 (xu3) — allow; perhaps. */
    public static final ChineseCharacterEntry 许_ALLOW_PERHAPS = entry("许")
            .py(X, V, Body.V, Tail.NONE, T3).strokes(6).radical(149)
            .leftRight(YAN_ZI_PANG, zi("午"))
            .phonoSemantic(YAN_ZI_PANG, zi("午"));

    public static final List<ChineseCharacterEntry> ALL = List.of(许_ALLOW_PERHAPS);
}
