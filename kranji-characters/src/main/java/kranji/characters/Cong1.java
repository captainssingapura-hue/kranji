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

/** Characters pronounced cong (tone 1). */
public final class Cong1 {
    private Cong1() {}

    /** 聪 (cong1) — clever; smart. */
    public static final ChineseCharacterEntry 聪_CLEVER_SMART = entry("聪")
            .py(C, OPEN, Body.O, Tail.NG, T1).strokes(15).radical(128)
            .leftRight(zi("耳"), zi("总"))
            .phonoSemantic(zi("耳"), zi("总"));

    public static final List<ChineseCharacterEntry> ALL = List.of(聪_CLEVER_SMART);
}
