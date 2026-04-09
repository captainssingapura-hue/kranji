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

/** Characters pronounced cheng (tone 2). */
public final class Cheng2 {
    private Cheng2() {}

    /** 成 (cheng2) — become. */
    public static final ChineseCharacterEntry 成_BECOME = entry("成")
            .py(CH, OPEN, Body.E, Tail.NG, T2).strokes(6).radical(62)
            .singular()
            .compoundIndicative("become");

    /** 城 (cheng2) — city; wall. */
    public static final ChineseCharacterEntry 城_CITY_WALL = entry("城")
            .py(CH, OPEN, Body.E, Tail.NG, T2).strokes(9).radical(32)
            .leftRight(zi("土"), zi("成"))
            .phonoSemantic(zi("土"), zi("成"));

    /** 程 (cheng2) — procedure. */
    public static final ChineseCharacterEntry 程_PROCEDURE = entry("程")
            .py(CH, OPEN, Body.E, Tail.NG, T2).strokes(12).radical(115)
            .leftRight(zi("禾"), zi("呈"))
            .phonoSemantic(zi("禾"), zi("呈"));

    /** 乘 (cheng2) — ride; multiply. */
    public static final ChineseCharacterEntry 乘_RIDE_MULTIPLY = entry("乘")
            .py(CH, OPEN, Body.E, Tail.NG, T2).strokes(10).radical(4)
            .singular()
            .compoundIndicative("ride; multiply");

    /** 诚 (cheng2) — sincere; honest. */
    public static final ChineseCharacterEntry 诚_SINCERE_HONEST = entry("诚")
            .py(CH, OPEN, Body.E, Tail.NG, T2).strokes(8).radical(149)
            .leftRight(YAN_ZI_PANG, zi("成"))
            .phonoSemantic(YAN_ZI_PANG, zi("成"));

    public static final List<ChineseCharacterEntry> ALL = List.of(成_BECOME, 城_CITY_WALL, 程_PROCEDURE, 乘_RIDE_MULTIPLY, 诚_SINCERE_HONEST);
}
