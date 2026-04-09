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

/** Characters pronounced pa (tone 2). */
public final class Pa2 {
    private Pa2() {}

    /** 爬 (pa2) — climb; crawl. */
    public static final ChineseCharacterEntry 爬_CLIMB_CRAWL = entry("爬")
            .py(P, OPEN, Body.A, Tail.NONE, T2).strokes(8).radical(87)
            .leftRight(zi("爪"), zi("巴"))
            .phonoSemantic(zi("爪"), zi("巴"));

    public static final List<ChineseCharacterEntry> ALL = List.of(爬_CLIMB_CRAWL);
}
