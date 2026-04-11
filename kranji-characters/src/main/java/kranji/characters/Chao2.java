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

/** Characters pronounced chao (tone 2). */
public final class Chao2 {
    private Chao2() {}

    /** 朝 (chao2) — dynasty; toward. */
    public static final ChineseCharacterEntry 朝_DYNASTY_TOWARD = entry("朝")
            .py(CH, OPEN, Body.A, Tail.VOWEL_U, T2).strokes(12).radical(74)
            .leftRight(zi("龺"), zi("月"))
            .phonoSemantic(zi("月"), zi("龺"));

    public static final List<ChineseCharacterEntry> ALL = List.of(朝_DYNASTY_TOWARD);
}
