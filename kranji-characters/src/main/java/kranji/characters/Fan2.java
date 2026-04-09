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

/** Characters pronounced fan (tone 2). */
public final class Fan2 {
    private Fan2() {}

    /** 烦 (fan2) — annoyed; bother. */
    public static final ChineseCharacterEntry 烦_ANNOYED_BOTHER = entry("烦")
            .py(F, OPEN, Body.A, Tail.N, T2).strokes(10).radical(86)
            .leftRight(zi("火"), zi("页"))
            .phonoSemantic(zi("火"), zi("页"));

    public static final List<ChineseCharacterEntry> ALL = List.of(烦_ANNOYED_BOTHER);
}
