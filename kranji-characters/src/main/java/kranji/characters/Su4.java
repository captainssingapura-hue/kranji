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

/** Characters pronounced su (tone 4). */
public final class Su4 {
    private Su4() {}

    /** 速 (su4) — speed; fast. */
    public static final ChineseCharacterEntry 速_SPEED_FAST = entry("速")
            .py(S, U, Body.U, Tail.NONE, T4).strokes(10).radical(162)
            .semiEnclosureBL(ZOU_ZHI_DI, zi("束"))
            .phonoSemantic(ZOU_ZHI_DI, zi("束"));

    /** 诉 (su4) — tell; accuse. */
    public static final ChineseCharacterEntry 诉_TELL_ACCUSE = entry("诉")
            .py(S, U, Body.U, Tail.NONE, T4).strokes(7).radical(149)
            .leftRight(YAN_ZI_PANG, zi("斥"))
            .phonoSemantic(YAN_ZI_PANG, zi("斥"));

    /** 素 (su4) — element; plain. */
    public static final ChineseCharacterEntry 素_ELEMENT_PLAIN = entry("素")
            .py(S, U, Body.U, Tail.NONE, T4).strokes(10).radical(120)
            .topBottom(zi("龶"), zi("糸"))
            .compoundIndicative("element; plain");

    public static final List<ChineseCharacterEntry> ALL = List.of(速_SPEED_FAST, 诉_TELL_ACCUSE, 素_ELEMENT_PLAIN);
}
