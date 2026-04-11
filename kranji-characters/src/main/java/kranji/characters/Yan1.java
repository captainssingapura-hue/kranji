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

/** Characters pronounced yan (tone 1). */
public final class Yan1 {
    private Yan1() {}

    /** 烟 (yan1) — smoke; tobacco. */
    public static final ChineseCharacterEntry 烟_SMOKE_TOBACCO = entry("烟")
            .py(ZERO, I, Body.A, Tail.N, T1).strokes(10).radical(86)
            .leftRight(zi("火"), zi("因"))
            .phonoSemantic(zi("火"), zi("因"));

    public static final List<ChineseCharacterEntry> ALL = List.of(烟_SMOKE_TOBACCO);
}
