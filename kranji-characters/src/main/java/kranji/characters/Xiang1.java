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

/** Characters pronounced xiang (tone 1). */
public final class Xiang1 {
    private Xiang1() {}

    /** 相 (xiang1) — mutual; look. */
    public static final ChineseCharacterEntry 相_MUTUAL_LOOK = entry("相")
            .py(X, I, Body.A, Tail.NG, T1).strokes(9).radical(109)
            .leftRight(zi("木"), zi("目"))
            .compoundIndicative("mutual; look");

    /** 香 (xiang1) — fragrant. */
    public static final ChineseCharacterEntry 香_FRAGRANT = entry("香")
            .py(X, I, Body.A, Tail.NG, T1).strokes(9).radical(186)
            .topBottom(zi("禾"), zi("日"))
            .compoundIndicative("fragrant");

    public static final List<ChineseCharacterEntry> ALL = List.of(相_MUTUAL_LOOK, 香_FRAGRANT);
}
