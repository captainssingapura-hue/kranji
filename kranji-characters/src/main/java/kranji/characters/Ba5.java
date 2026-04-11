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

/** Characters pronounced ba (tone 5). */
public final class Ba5 {
    private Ba5() {}

    /** 吧 (ba5) — particle. */
    public static final ChineseCharacterEntry 吧_PARTICLE = entry("吧")
            .py(B, OPEN, Body.A, Tail.NONE, T5).strokes(7).radical(30)
            .leftRight(zi("口"), zi("巴"))
            .phonoSemantic(zi("口"), zi("巴"));

    public static final List<ChineseCharacterEntry> ALL = List.of(吧_PARTICLE);
}
