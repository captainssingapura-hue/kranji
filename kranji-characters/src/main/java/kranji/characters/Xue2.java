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

/** Characters pronounced xue (tone 2). */
public final class Xue2 {
    private Xue2() {}

    /** 学 (xue2) — learn; study. */
    public static final ChineseCharacterEntry 学_LEARN_STUDY = entry("学")
            .py(X, V, Body.E_CARON, Tail.NONE, T2).strokes(8).radical(39)
            .topBottom(zi("⺍"), zi("子"))
            .compoundIndicative("learn; study");

    public static final List<ChineseCharacterEntry> ALL = List.of(学_LEARN_STUDY);
}
