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

/** Characters pronounced la (tone 5). */
public final class La5 {
    private La5() {}

    /** 啦 (la5) — particle. */
    public static final ChineseCharacterEntry 啦_PARTICLE = entry("啦")
            .py(L, OPEN, Body.A, Tail.NONE, T5).strokes(11).radical(30)
            .leftRight(zi("口"), zi("拉"))
            .phonoSemantic(zi("口"), zi("拉"));

    public static final List<ChineseCharacterEntry> ALL = List.of(啦_PARTICLE);
}
