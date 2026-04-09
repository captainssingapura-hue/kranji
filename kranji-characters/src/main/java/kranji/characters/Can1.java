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

/** Characters pronounced can (tone 1). */
public final class Can1 {
    private Can1() {}

    /** 参 (can1) — participate. */
    public static final ChineseCharacterEntry 参_PARTICIPATE = entry("参")
            .py(C, OPEN, Body.A, Tail.N, T1).strokes(8).radical(28)
            .topBottom(zi("厶"), zi("大彡"))
            .compoundIndicative("participate");

    public static final List<ChineseCharacterEntry> ALL = List.of(参_PARTICIPATE);
}
