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

/** Characters pronounced ya (tone 1). */
public final class Ya1 {
    private Ya1() {}

    /** 压 (ya1) — press; pressure. */
    public static final ChineseCharacterEntry 压_PRESS_PRESSURE = entry("压")
            .py(ZERO, I, Body.A, Tail.NONE, T1).strokes(6).radical(27)
            .semiEnclosureUL(zi("厂"), zi("圡"))
            .phonoSemantic(zi("厂"), zi("圡"));

    /** 鸭 (ya1) — duck. */
    public static final ChineseCharacterEntry 鸭_DUCK = entry("鸭")
            .py(ZERO, I, Body.A, Tail.NONE, T1).strokes(10).radical(196)
            .leftRight(zi("甲"), zi("鸟"))
            .phonoSemantic(zi("鸟"), zi("甲"));

    public static final List<ChineseCharacterEntry> ALL = List.of(压_PRESS_PRESSURE, 鸭_DUCK);
}
