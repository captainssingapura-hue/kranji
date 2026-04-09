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

/** Characters pronounced wen (tone 2). */
public final class Wen2 {
    private Wen2() {}

    /** 文 (wen2) — writing; text. */
    public static final ChineseCharacterEntry 文_WRITING_TEXT = entry("文")
            .py(ZERO, U, Body.E, Tail.N, T2).strokes(4).radical(67)
            .singular()
            .pictograph();

    /** 闻 (wen2) — smell; hear. */
    public static final ChineseCharacterEntry 闻_SMELL_HEAR = entry("闻")
            .py(ZERO, U, Body.E, Tail.N, T2).strokes(9).radical(169)
            .semiEnclosureT3(zi("门"), zi("耳"))
            .compoundIndicative("smell; hear");

    /** 蚊 (wen2) — mosquito. */
    public static final ChineseCharacterEntry 蚊_MOSQUITO = entry("蚊")
            .py(ZERO, U, Body.E, Tail.N, T2).strokes(10).radical(142)
            .leftRight(zi("虫"), zi("文"))
            .phonoSemantic(zi("虫"), zi("文"));

    public static final List<ChineseCharacterEntry> ALL = List.of(文_WRITING_TEXT, 闻_SMELL_HEAR, 蚊_MOSQUITO);
}
