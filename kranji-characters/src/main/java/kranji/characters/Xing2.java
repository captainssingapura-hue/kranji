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

/** Characters pronounced xing (tone 2). */
public final class Xing2 {
    private Xing2() {}

    /** 行 (xing2) — walk; go. */
    public static final ChineseCharacterEntry 行_WALK_GO = entry("行")
            .py(X, OPEN, Body.I, Tail.NG, T2).strokes(6).radical(144)
            .leftRight(SHUANG_REN_PANG, zi("亍"))
            .compoundIndicative("walk; go");

    /** 型 (xing2) — type; model. */
    public static final ChineseCharacterEntry 型_TYPE_MODEL = entry("型")
            .py(X, OPEN, Body.I, Tail.NG, T2).strokes(9).radical(32)
            .leftRight(zi("刑"), zi("土"))
            .phonoSemantic(zi("土"), zi("刑"));

    /** 形 (xing2) — shape; form. */
    public static final ChineseCharacterEntry 形_SHAPE_FORM = entry("形")
            .py(X, OPEN, Body.I, Tail.NG, T2).strokes(7).radical(59)
            .leftRight(zi("开"), zi("彡"))
            .phonoSemantic(zi("彡"), zi("开"));

    public static final List<ChineseCharacterEntry> ALL = List.of(行_WALK_GO, 型_TYPE_MODEL, 形_SHAPE_FORM);
}
