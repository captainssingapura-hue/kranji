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

/** Characters pronounced na (tone 2). */
public final class Na2 {
    private Na2() {}

    /** 拿 (na2) — take; hold. */
    public static final ChineseCharacterEntry 拿_TAKE_HOLD = entry("拿")
            .py(N, OPEN, Body.A, Tail.NONE, T2).strokes(10).radical(64)
            .topBottom(zi("合"), zi("手"))
            .compoundIndicative("take; hold");

    public static final List<ChineseCharacterEntry> ALL = List.of(拿_TAKE_HOLD);
}
