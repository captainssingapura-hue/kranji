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

/** Characters pronounced men (tone 5). */
public final class Men5 {
    private Men5() {}

    /** 们 (men5) — plural marker. */
    public static final ChineseCharacterEntry 们_PLURAL_MARKER = entry("们")
            .py(M, OPEN, Body.E, Tail.N, T5).strokes(5).radical(9)
            .leftRight(DAN_REN_PANG, zi("门"))
            .phonoSemantic(DAN_REN_PANG, zi("门"));

    public static final List<ChineseCharacterEntry> ALL = List.of(们_PLURAL_MARKER);
}
