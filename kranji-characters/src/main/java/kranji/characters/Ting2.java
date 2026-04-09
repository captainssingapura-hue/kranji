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

/** Characters pronounced ting (tone 2). */
public final class Ting2 {
    private Ting2() {}

    /** 停 (ting2) — stop; halt. */
    public static final ChineseCharacterEntry 停_STOP_HALT = entry("停")
            .py(T, OPEN, Body.I, Tail.NG, T2).strokes(11).radical(9)
            .leftRight(DAN_REN_PANG, zi("亭"))
            .phonoSemantic(DAN_REN_PANG, zi("亭"));

    public static final List<ChineseCharacterEntry> ALL = List.of(停_STOP_HALT);
}
