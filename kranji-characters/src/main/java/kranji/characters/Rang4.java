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

/** Characters pronounced rang (tone 4). */
public final class Rang4 {
    private Rang4() {}

    /** 让 (rang4) — let; allow. */
    public static final ChineseCharacterEntry 让_LET_ALLOW = entry("让")
            .py(R, OPEN, Body.A, Tail.NG, T4).strokes(5).radical(149)
            .leftRight(YAN_ZI_PANG, zi("上"))
            .phonoSemantic(YAN_ZI_PANG, zi("上"));

    public static final List<ChineseCharacterEntry> ALL = List.of(让_LET_ALLOW);
}
