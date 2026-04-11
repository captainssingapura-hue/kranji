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

/** Characters pronounced dong (tone 4). */
public final class Dong4 {
    private Dong4() {}

    /** 动 (dong4) — move. */
    public static final ChineseCharacterEntry 动_MOVE = entry("动")
            .py(D, OPEN, Body.O, Tail.NG, T4).strokes(6).radical(19)
            .leftRight(zi("云"), zi("力"))
            .phonoSemantic(zi("力"), zi("云"));

    public static final List<ChineseCharacterEntry> ALL = List.of(动_MOVE);
}
