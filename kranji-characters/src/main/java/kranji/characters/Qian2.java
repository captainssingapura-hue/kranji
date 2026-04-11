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

/** Characters pronounced qian (tone 2). */
public final class Qian2 {
    private Qian2() {}

    /** 前 (qian2) — front; before. */
    public static final ChineseCharacterEntry 前_FRONT_BEFORE = entry("前")
            .py(Q, I, Body.A, Tail.N, T2).strokes(9).radical(18)
            .topBottom(zi("前"), LI_DAO_PANG)
            .compoundIndicative("front; before");

    /** 钱 (qian2) — money. */
    public static final ChineseCharacterEntry 钱_MONEY = entry("钱")
            .py(Q, I, Body.A, Tail.N, T2).strokes(10).radical(167)
            .leftRight(JIN_ZI_PANG, zi("戋"))
            .phonoSemantic(JIN_ZI_PANG, zi("戋"));

    public static final List<ChineseCharacterEntry> ALL = List.of(前_FRONT_BEFORE, 钱_MONEY);
}
