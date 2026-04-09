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

/** Characters pronounced shu (tone 1). */
public final class Shu1 {
    private Shu1() {}

    /** 书 (shu1) — book. */
    public static final ChineseCharacterEntry 书_BOOK = entry("书")
            .py(SH, U, Body.U, Tail.NONE, T1).strokes(4).radical(73)
            .singular()
            .pictograph();

    /** 输 (shu1) — lose; transport. */
    public static final ChineseCharacterEntry 输_LOSE_TRANSPORT = entry("输")
            .py(SH, U, Body.U, Tail.NONE, T1).strokes(16).radical(159)
            .leftRight(zi("车"), zi("俞"))
            .phonoSemantic(zi("车"), zi("俞"));

    /** 叔 (shu1) — uncle. */
    public static final ChineseCharacterEntry 叔_UNCLE = entry("叔")
            .py(SH, U, Body.U, Tail.NONE, T1).strokes(8).radical(29)
            .topBottom(zi("上小"), zi("又"))
            .compoundIndicative("uncle");

    public static final List<ChineseCharacterEntry> ALL = List.of(书_BOOK, 输_LOSE_TRANSPORT, 叔_UNCLE);
}
