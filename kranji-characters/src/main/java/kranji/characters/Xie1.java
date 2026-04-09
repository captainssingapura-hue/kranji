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

/** Characters pronounced xie (tone 1). */
public final class Xie1 {
    private Xie1() {}

    /** 些 (xie1) — some. */
    public static final ChineseCharacterEntry 些_SOME = entry("些")
            .py(X, I, Body.E_CARON, Tail.NONE, T1).strokes(8).radical(7)
            .topBottom(zi("此"), zi("二"))
            .compoundIndicative("some");

    public static final List<ChineseCharacterEntry> ALL = List.of(些_SOME);
}
