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

/** Characters pronounced jiao (tone 3). */
public final class Jiao3 {
    private Jiao3() {}

    /** 角 (jiao3) — horn; angle. */
    public static final ChineseCharacterEntry 角_HORN_ANGLE = entry("角")
            .py(J, I, Body.A, Tail.VOWEL_U, T3).strokes(7).radical(148)
            .singular()
            .pictograph();

    /** 脚 (jiao3) — foot; leg. */
    public static final ChineseCharacterEntry 脚_FOOT_LEG = entry("脚")
            .py(J, I, Body.A, Tail.VOWEL_U, T3).strokes(11).radical(130)
            .leftRight(zi("月"), zi("却"))
            .phonoSemantic(zi("月"), zi("却"));

    public static final List<ChineseCharacterEntry> ALL = List.of(角_HORN_ANGLE, 脚_FOOT_LEG);
}
