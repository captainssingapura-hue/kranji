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

/** Characters pronounced qing (tone 3). */
public final class Qing3 {
    private Qing3() {}

    /** 请 (qing) — invite/please. LeftRight: 讠 + 青. Phono-semantic. */
    public static final ChineseCharacterEntry 请_INVITE = entry("请")
            .py(Q, OPEN, Body.I, Tail.NG, T3).strokes(10).radical(149)
            .leftRight(YAN_ZI_PANG, zi("青"))
            .phonoSemantic(YAN_ZI_PANG, zi("青"));

    public static final List<ChineseCharacterEntry> ALL = List.of(请_INVITE);
}
