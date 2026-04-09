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

/** Characters pronounced jian (tone 1). */
public final class Jian1 {
    private Jian1() {}

    /** 间 (jian1) — between. */
    public static final ChineseCharacterEntry 间_BETWEEN = entry("间")
            .py(J, I, Body.A, Tail.N, T1).strokes(7).radical(169)
            .semiEnclosureT3(zi("门"), zi("日"))
            .compoundIndicative("between");

    /** 坚 (jian1) — hard; firm. */
    public static final ChineseCharacterEntry 坚_HARD_FIRM = entry("坚")
            .py(J, I, Body.A, Tail.N, T1).strokes(7).radical(32)
            .topBottom(zi("臤"), zi("土"))
            .phonoSemantic(zi("土"), zi("臤"));

    /** 肩 (jian1) — shoulder. */
    public static final ChineseCharacterEntry 肩_SHOULDER = entry("肩")
            .py(J, I, Body.A, Tail.N, T1).strokes(8).radical(130)
            .topBottom(zi("户"), zi("月"))
            .compoundIndicative("shoulder");

    public static final List<ChineseCharacterEntry> ALL = List.of(间_BETWEEN, 坚_HARD_FIRM, 肩_SHOULDER);
}
