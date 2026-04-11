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

/** Characters pronounced chuang (tone 1). */
public final class Chuang1 {
    private Chuang1() {}

    /** 窗 (chuang1) — window. */
    public static final ChineseCharacterEntry 窗_WINDOW = entry("窗")
            .py(CH, U, Body.A, Tail.NG, T1).strokes(12).radical(116)
            .topBottom(zi("穴"), zi("囱"))
            .phonoSemantic(zi("穴"), zi("囱"));

    public static final List<ChineseCharacterEntry> ALL = List.of(窗_WINDOW);
}
