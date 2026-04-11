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

/** Characters pronounced ku (tone 1). */
public final class Ku1 {
    private Ku1() {}

    /** 哭 (ku1) — cry. */
    public static final ChineseCharacterEntry 哭_CRY = entry("哭")
            .py(K, U, Body.U, Tail.NONE, T1).strokes(10).radical(30)
            .topBottom(zi("口口"), zi("犬"))
            .compoundIndicative("cry");

    public static final List<ChineseCharacterEntry> ALL = List.of(哭_CRY);
}
