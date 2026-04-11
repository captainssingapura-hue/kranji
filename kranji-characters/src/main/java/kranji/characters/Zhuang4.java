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

/** Characters pronounced zhuang (tone 4). */
public final class Zhuang4 {
    private Zhuang4() {}

    /** 状 (zhuang4) — shape; state. */
    public static final ChineseCharacterEntry 状_SHAPE_STATE = entry("状")
            .py(ZH, U, Body.A, Tail.NG, T4).strokes(7).radical(90)
            .leftRight(zi("丬"), zi("犬"))
            .phonoSemantic(zi("犬"), zi("丬"));

    public static final List<ChineseCharacterEntry> ALL = List.of(状_SHAPE_STATE);
}
