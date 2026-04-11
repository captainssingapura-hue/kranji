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

/** Characters pronounced zu (tone 1). */
public final class Zu1 {
    private Zu1() {}

    /** 租 (zu1) — rent. */
    public static final ChineseCharacterEntry 租_RENT = entry("租")
            .py(Z, U, Body.U, Tail.NONE, T1).strokes(10).radical(115)
            .leftRight(zi("禾"), zi("且"))
            .phonoSemantic(zi("禾"), zi("且"));

    public static final List<ChineseCharacterEntry> ALL = List.of(租_RENT);
}
