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

/** Characters pronounced quan (tone 2). */
public final class Quan2 {
    private Quan2() {}

    /** 全 (quan2) — whole; all. */
    public static final ChineseCharacterEntry 全_WHOLE_ALL = entry("全")
            .py(Q, V, Body.A, Tail.N, T2).strokes(6).radical(9)
            .topBottom(zi("人"), zi("王"))
            .compoundIndicative("whole; all");

    /** 权 (quan2) — right; power. */
    public static final ChineseCharacterEntry 权_RIGHT_POWER = entry("权")
            .py(Q, V, Body.A, Tail.N, T2).strokes(6).radical(75)
            .leftRight(zi("木"), zi("又"))
            .phonoSemantic(zi("木"), zi("又"));

    /** 泉 (quan2) — spring; source. */
    public static final ChineseCharacterEntry 泉_SPRING_SOURCE = entry("泉")
            .py(Q, V, Body.A, Tail.N, T2).strokes(9).radical(85)
            .topBottom(zi("白"), zi("水"))
            .compoundIndicative("spring; source");

    /** 拳 (quan2) — fist. */
    public static final ChineseCharacterEntry 拳_FIST = entry("拳")
            .py(Q, V, Body.A, Tail.N, T2).strokes(10).radical(64)
            .topBottom(zi("龹"), zi("手"))
            .compoundIndicative("fist");

    public static final List<ChineseCharacterEntry> ALL = List.of(全_WHOLE_ALL, 权_RIGHT_POWER, 泉_SPRING_SOURCE, 拳_FIST);
}
