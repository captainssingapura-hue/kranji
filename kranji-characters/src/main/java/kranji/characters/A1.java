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

/** Characters pronounced a (tone 1). */
public final class A1 {
    private A1() {}

    /** 啊 (a1) — exclamation. */
    public static final ChineseCharacterEntry 啊_EXCLAMATION = entry("啊")
            .py(ZERO, OPEN, Body.A, Tail.NONE, T1).strokes(10).radical(30)
            .leftRight(zi("口"), zi("阿"))
            .phonoSemantic(zi("口"), zi("阿"));

    public static final List<ChineseCharacterEntry> ALL = List.of(啊_EXCLAMATION);
}
