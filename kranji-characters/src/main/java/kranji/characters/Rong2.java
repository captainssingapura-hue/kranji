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

/** Characters pronounced rong (tone 2). */
public final class Rong2 {
    private Rong2() {}

    /** 容 (rong2) — contain; face. */
    public static final ChineseCharacterEntry 容_CONTAIN_FACE = entry("容")
            .py(R, OPEN, Body.O, Tail.NG, T2).strokes(10).radical(40)
            .topBottom(BAO_GAI_TOU, zi("谷"))
            .phonoSemantic(BAO_GAI_TOU, zi("谷"));

    public static final List<ChineseCharacterEntry> ALL = List.of(容_CONTAIN_FACE);
}
