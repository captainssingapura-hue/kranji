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

/** Characters pronounced zhuan (tone 3). */
public final class Zhuan3 {
    private Zhuan3() {}

    /** 转 (zhuan3) — turn; change. */
    public static final ChineseCharacterEntry 转_TURN_CHANGE = entry("转")
            .py(ZH, U, Body.A, Tail.N, T3).strokes(8).radical(159)
            .leftRight(zi("车"), zi("专"))
            .phonoSemantic(zi("车"), zi("专"));

    public static final List<ChineseCharacterEntry> ALL = List.of(转_TURN_CHANGE);
}
