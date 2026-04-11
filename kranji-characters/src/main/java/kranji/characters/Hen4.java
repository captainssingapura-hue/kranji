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

/** Characters pronounced hen (tone 4). */
public final class Hen4 {
    private Hen4() {}

    /** 恨 (hen4) — hate; regret. */
    public static final ChineseCharacterEntry 恨_HATE_REGRET = entry("恨")
            .py(H, OPEN, Body.E, Tail.N, T4).strokes(9).radical(61)
            .leftRight(SHU_XIN_PANG, zi("艮"))
            .phonoSemantic(SHU_XIN_PANG, zi("艮"));

    public static final List<ChineseCharacterEntry> ALL = List.of(恨_HATE_REGRET);
}
