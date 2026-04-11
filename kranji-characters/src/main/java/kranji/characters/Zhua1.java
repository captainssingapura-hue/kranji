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

/** Characters pronounced zhua (tone 1). */
public final class Zhua1 {
    private Zhua1() {}

    /** 抓 (zhua1) — grab; catch. */
    public static final ChineseCharacterEntry 抓_GRAB_CATCH = entry("抓")
            .py(ZH, U, Body.A, Tail.NONE, T1).strokes(7).radical(64)
            .leftRight(TI_SHOU_PANG, zi("爪"))
            .phonoSemantic(TI_SHOU_PANG, zi("爪"));

    public static final List<ChineseCharacterEntry> ALL = List.of(抓_GRAB_CATCH);
}
