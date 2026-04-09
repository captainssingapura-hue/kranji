package kranji.characters;

import kranji.entry.ChineseCharacterEntry;

import java.util.List;

import static kranji.characters.Comp.*;
import static kranji.characters.EntryBuilder.entry;
import static kranji.component.Parts.*;
import static kranji.pinyin.Initial.*;
import static kranji.pinyin.Head.*;
import kranji.pinyin.Body;
import kranji.pinyin.Tail;

/** Characters pronounced chang (tone 4). */
public final class Chang4 {
    private Chang4() {}

    /** 唱 (chang4) — sing. */
    public static final ChineseCharacterEntry 唱_SING = entry("唱")
            .py(CH, OPEN, Body.A, Tail.NG, T4).strokes(11).radical(30)
            .leftRight(zi("口"), zi("昌"))
            .phonoSemantic(zi("口"), zi("昌"));

    public static final List<ChineseCharacterEntry> ALL = List.of(唱_SING);
}
