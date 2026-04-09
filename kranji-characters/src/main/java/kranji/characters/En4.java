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

/** Characters pronounced en (tone 4). */
public final class En4 {
    private En4() {}

    /** 嗯 (en4) — interjection. */
    public static final ChineseCharacterEntry 嗯_INTERJECTION = entry("嗯")
            .py(ZERO, OPEN, Body.E, Tail.N, T4).strokes(13).radical(30)
            .leftRight(zi("口"), zi("恩"))
            .phonoSemantic(zi("口"), zi("恩"));

    public static final List<ChineseCharacterEntry> ALL = List.of(嗯_INTERJECTION);
}
