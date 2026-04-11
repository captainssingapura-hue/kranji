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

/** Characters pronounced zi (tone 1). */
public final class Zi1 {
    private Zi1() {}

    /** 资 (zi1) — resources. */
    public static final ChineseCharacterEntry 资_RESOURCES = entry("资")
            .py(Z, OPEN, Body.NULL, Tail.NONE, T1).strokes(10).radical(154)
            .topBottom(zi("次"), zi("贝"))
            .phonoSemantic(zi("贝"), zi("次"));

    public static final List<ChineseCharacterEntry> ALL = List.of(资_RESOURCES);
}
