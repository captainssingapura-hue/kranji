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

/** Characters pronounced fan (tone 1). */
public final class Fan1 {
    private Fan1() {}

    /** 翻 (fan1) — turn over; flip. */
    public static final ChineseCharacterEntry 翻_TURN_OVER_FLIP = entry("翻")
            .py(F, OPEN, Body.A, Tail.N, T1).strokes(18).radical(124)
            .leftRight(zi("番"), zi("羽"))
            .phonoSemantic(zi("羽"), zi("番"));

    public static final List<ChineseCharacterEntry> ALL = List.of(翻_TURN_OVER_FLIP);
}
