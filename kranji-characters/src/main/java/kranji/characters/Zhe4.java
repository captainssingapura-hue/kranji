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

/** Characters pronounced zhe (tone 4). */
public final class Zhe4 {
    private Zhe4() {}

    /** 这 (zhe4) — this. */
    public static final ChineseCharacterEntry 这_THIS = entry("这")
            .py(ZH, OPEN, Body.E, Tail.NONE, T4).strokes(7).radical(162)
            .semiEnclosureBL(ZOU_ZHI_DI, zi("文"))
            .phonoSemantic(ZOU_ZHI_DI, zi("文"));

    public static final List<ChineseCharacterEntry> ALL = List.of(这_THIS);
}
