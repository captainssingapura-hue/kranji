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

/** Characters pronounced tong (tone 3). */
public final class Tong3 {
    private Tong3() {}

    /** 统 (tong3) — unite; system. */
    public static final ChineseCharacterEntry 统_UNITE_SYSTEM = entry("统")
            .py(T, OPEN, Body.O, Tail.NG, T3).strokes(9).radical(120)
            .leftRight(JIAO_SI_PANG, zi("充"))
            .phonoSemantic(JIAO_SI_PANG, zi("充"));

    public static final List<ChineseCharacterEntry> ALL = List.of(统_UNITE_SYSTEM);
}
