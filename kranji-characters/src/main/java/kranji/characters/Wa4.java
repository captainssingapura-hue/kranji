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

/** Characters pronounced wa (tone 4). */
public final class Wa4 {
    private Wa4() {}

    /** 袜 (wa4) — socks. */
    public static final ChineseCharacterEntry 袜_SOCKS = entry("袜")
            .py(ZERO, U, Body.A, Tail.NONE, T4).strokes(10).radical(145)
            .leftRight(YI_ZI_PANG, zi("末"))
            .phonoSemantic(YI_ZI_PANG, zi("末"));

    public static final List<ChineseCharacterEntry> ALL = List.of(袜_SOCKS);
}
