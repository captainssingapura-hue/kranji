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

/** Characters pronounced suo (tone 3). */
public final class Suo3 {
    private Suo3() {}

    /** 所 (suo3) — place. */
    public static final ChineseCharacterEntry 所_PLACE = entry("所")
            .py(S, U, Body.O, Tail.NONE, T3).strokes(8).radical(63)
            .leftRight(zi("户"), zi("斤"))
            .compoundIndicative("place");

    public static final List<ChineseCharacterEntry> ALL = List.of(所_PLACE);
}
