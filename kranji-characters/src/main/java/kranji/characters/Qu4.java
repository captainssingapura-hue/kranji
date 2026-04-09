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

/** Characters pronounced qu (tone 4). */
public final class Qu4 {
    private Qu4() {}

    /** 去 (qu4) — go; leave. */
    public static final ChineseCharacterEntry 去_GO_LEAVE = entry("去")
            .py(Q, V, Body.V, Tail.NONE, T4).strokes(5).radical(28)
            .topBottom(zi("土"), zi("厶"))
            .compoundIndicative("go; leave");

    /** 趣 (qu4) — interest; fun. */
    public static final ChineseCharacterEntry 趣_INTEREST_FUN = entry("趣")
            .py(Q, V, Body.V, Tail.NONE, T4).strokes(15).radical(156)
            .semiEnclosureBL(zi("走"), zi("取"))
            .phonoSemantic(zi("走"), zi("取"));

    public static final List<ChineseCharacterEntry> ALL = List.of(去_GO_LEAVE, 趣_INTEREST_FUN);
}
