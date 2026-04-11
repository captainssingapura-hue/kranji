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

/** Characters pronounced tan (tone 4). */
public final class Tan4 {
    private Tan4() {}

    /** 叹 (tan4) — sigh. */
    public static final ChineseCharacterEntry 叹_SIGH = entry("叹")
            .py(T, OPEN, Body.A, Tail.N, T4).strokes(5).radical(30)
            .leftRight(zi("口"), zi("又"))
            .phonoSemantic(zi("口"), zi("又"));

    public static final List<ChineseCharacterEntry> ALL = List.of(叹_SIGH);
}
