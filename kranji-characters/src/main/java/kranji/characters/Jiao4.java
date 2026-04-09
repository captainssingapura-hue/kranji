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

/** Characters pronounced jiao (tone 4). */
public final class Jiao4 {
    private Jiao4() {}

    /** 叫 (jiao4) — call; shout. */
    public static final ChineseCharacterEntry 叫_CALL_SHOUT = entry("叫")
            .py(J, I, Body.A, Tail.VOWEL_U, T4).strokes(5).radical(30)
            .leftRight(zi("口"), zi("丩"))
            .phonoSemantic(zi("口"), zi("丩"));

    /** 教 (jiao4) — teach. */
    public static final ChineseCharacterEntry 教_TEACH = entry("教")
            .py(J, I, Body.A, Tail.VOWEL_U, T4).strokes(11).radical(66)
            .leftRight(zi("孝"), FAN_WEN_PANG)
            .phonoSemantic(FAN_WEN_PANG, zi("孝"));

    /** 较 (jiao4) — compare; rather. */
    public static final ChineseCharacterEntry 较_COMPARE_RATHER = entry("较")
            .py(J, I, Body.A, Tail.VOWEL_U, T4).strokes(10).radical(159)
            .leftRight(zi("车"), zi("交"))
            .phonoSemantic(zi("车"), zi("交"));

    public static final List<ChineseCharacterEntry> ALL = List.of(叫_CALL_SHOUT, 教_TEACH, 较_COMPARE_RATHER);
}
