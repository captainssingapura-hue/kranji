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

/** Characters pronounced duo (tone 1). */
public final class Duo1 {
    private Duo1() {}

    /** 多 (duo1) — many; much. */
    public static final ChineseCharacterEntry 多_MANY_MUCH = entry("多")
            .py(D, U, Body.O, Tail.NONE, T1).strokes(6).radical(36)
            .topBottom(zi("夕"), zi("夕"))
            .compoundIndicative("many; much");

    public static final List<ChineseCharacterEntry> ALL = List.of(多_MANY_MUCH);
}
