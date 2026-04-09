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

/** Characters pronounced e (tone 2). */
public final class E2 {
    private E2() {}

    /** 额 (e2) — forehead; sum. */
    public static final ChineseCharacterEntry 额_FOREHEAD_SUM = entry("额")
            .py(ZERO, OPEN, Body.E, Tail.NONE, T2).strokes(15).radical(181)
            .leftRight(zi("客"), zi("页"))
            .phonoSemantic(zi("页"), zi("客"));

    /** 鹅 (e2) — goose. */
    public static final ChineseCharacterEntry 鹅_GOOSE = entry("鹅")
            .py(ZERO, OPEN, Body.E, Tail.NONE, T2).strokes(12).radical(196)
            .leftRight(zi("我"), zi("鸟"))
            .phonoSemantic(zi("鸟"), zi("我"));

    public static final List<ChineseCharacterEntry> ALL = List.of(额_FOREHEAD_SUM, 鹅_GOOSE);
}
