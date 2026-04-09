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

/** Characters pronounced fa (tone 1). */
public final class Fa1 {
    private Fa1() {}

    /** 发 (fa1) — send; emit. */
    public static final ChineseCharacterEntry 发_SEND_EMIT = entry("发")
            .py(F, OPEN, Body.A, Tail.NONE, T1).strokes(5).radical(29)
            .singular()
            .pictograph();

    public static final List<ChineseCharacterEntry> ALL = List.of(发_SEND_EMIT);
}
