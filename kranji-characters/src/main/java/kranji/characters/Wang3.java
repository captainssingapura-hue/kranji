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

/** Characters pronounced wang (tone 3). */
public final class Wang3 {
    private Wang3() {}

    /** 往 (wang3) — go; toward. */
    public static final ChineseCharacterEntry 往_GO_TOWARD = entry("往")
            .py(ZERO, U, Body.A, Tail.NG, T3).strokes(8).radical(60)
            .leftRight(SHUANG_REN_PANG, zi("王"))
            .phonoSemantic(SHUANG_REN_PANG, zi("王"));

    /** 网 (wang3) — net; web. */
    public static final ChineseCharacterEntry 网_NET_WEB = entry("网")
            .py(ZERO, U, Body.A, Tail.NG, T3).strokes(6).radical(120)
            .semiEnclosureT3(TONG_ZI_KUANG, zi("㐅"))
            .pictograph();

    public static final List<ChineseCharacterEntry> ALL = List.of(往_GO_TOWARD, 网_NET_WEB);
}
