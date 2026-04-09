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

/** Characters pronounced mo (tone 2). */
public final class Mo2 {
    private Mo2() {}

    /** 模 (mo2) — model; pattern. */
    public static final ChineseCharacterEntry 模_MODEL_PATTERN = entry("模")
            .py(M, OPEN, Body.O, Tail.NONE, T2).strokes(14).radical(75)
            .leftRight(zi("木"), zi("莫"))
            .phonoSemantic(zi("木"), zi("莫"));

    public static final List<ChineseCharacterEntry> ALL = List.of(模_MODEL_PATTERN);
}
