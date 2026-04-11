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

/** Characters pronounced na (tone 3). */
public final class Na3 {
    private Na3() {}

    /** 哪 (na3) — which; where. */
    public static final ChineseCharacterEntry 哪_WHICH_WHERE = entry("哪")
            .py(N, OPEN, Body.A, Tail.NONE, T3).strokes(9).radical(30)
            .leftRight(zi("口"), zi("那"))
            .phonoSemantic(zi("口"), zi("那"));

    public static final List<ChineseCharacterEntry> ALL = List.of(哪_WHICH_WHERE);
}
