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

/** Characters pronounced zhang (tone 1). */
public final class Zhang1 {
    private Zhang1() {}

    /** 张 (zhang1) — stretch; sheet. */
    public static final ChineseCharacterEntry 张_STRETCH_SHEET = entry("张")
            .py(ZH, OPEN, Body.A, Tail.NG, T1).strokes(7).radical(57)
            .leftRight(zi("弓"), zi("长"))
            .phonoSemantic(zi("弓"), zi("长"));

    /** 章 (zhang1) — chapter; stamp. */
    public static final ChineseCharacterEntry 章_CHAPTER_STAMP = entry("章")
            .py(ZH, OPEN, Body.A, Tail.NG, T1).strokes(11).radical(117)
            .topBottom(zi("立"), zi("早"))
            .compoundIndicative("chapter; stamp");

    public static final List<ChineseCharacterEntry> ALL = List.of(张_STRETCH_SHEET, 章_CHAPTER_STAMP);
}
