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

/** Characters pronounced wu (tone 4). */
public final class Wu4 {
    private Wu4() {}

    /** 物 (wu4) — thing; object. */
    public static final ChineseCharacterEntry 物_THING_OBJECT = entry("物")
            .py(ZERO, U, Body.U, Tail.NONE, T4).strokes(8).radical(93)
            .leftRight(zi("牜"), zi("勿"))
            .phonoSemantic(zi("牜"), zi("勿"));

    public static final List<ChineseCharacterEntry> ALL = List.of(物_THING_OBJECT);
}
