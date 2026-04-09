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

/** Characters pronounced zheng (tone 3). */
public final class Zheng3 {
    private Zheng3() {}

    /** 整 (zheng3) — whole; arrange. */
    public static final ChineseCharacterEntry 整_WHOLE_ARRANGE = entry("整")
            .py(ZH, OPEN, Body.E, Tail.NG, T3).strokes(16).radical(66)
            .topBottom(zi("敕束"), zi("正"))
            .phonoSemantic(FAN_WEN_PANG, zi("正"));

    public static final List<ChineseCharacterEntry> ALL = List.of(整_WHOLE_ARRANGE);
}
