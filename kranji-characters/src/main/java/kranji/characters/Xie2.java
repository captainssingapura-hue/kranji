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

/** Characters pronounced xie (tone 2). */
public final class Xie2 {
    private Xie2() {}

    /** 鞋 (xie2) — shoes. */
    public static final ChineseCharacterEntry 鞋_SHOES = entry("鞋")
            .py(X, I, Body.E_CARON, Tail.NONE, T2).strokes(15).radical(177)
            .leftRight(zi("革"), zi("圭"))
            .phonoSemantic(zi("革"), zi("圭"));

    /** 协 (xie2) — cooperate. */
    public static final ChineseCharacterEntry 协_COOPERATE = entry("协")
            .py(X, I, Body.E_CARON, Tail.NONE, T2).strokes(6).radical(24)
            .leftRight(zi("十"), zi("办"))
            .compoundIndicative("cooperate");

    public static final List<ChineseCharacterEntry> ALL = List.of(鞋_SHOES, 协_COOPERATE);
}
