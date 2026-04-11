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

/** Characters pronounced xu (tone 1). */
public final class Xu1 {
    private Xu1() {}

    /** 须 (xu1) — must; beard. */
    public static final ChineseCharacterEntry 须_MUST_BEARD = entry("须")
            .py(X, V, Body.V, Tail.NONE, T1).strokes(9).radical(181)
            .leftRight(zi("彡"), zi("页"))
            .phonoSemantic(zi("页"), zi("彡"));

    /** 需 (xu1) — need; require. */
    public static final ChineseCharacterEntry 需_NEED_REQUIRE = entry("需")
            .py(X, V, Body.V, Tail.NONE, T1).strokes(14).radical(173)
            .topBottom(zi("雨"), zi("而"))
            .phonoSemantic(zi("雨"), zi("而"));

    public static final List<ChineseCharacterEntry> ALL = List.of(须_MUST_BEARD, 需_NEED_REQUIRE);
}
