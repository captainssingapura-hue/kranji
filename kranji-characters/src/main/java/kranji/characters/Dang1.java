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

/** Characters pronounced dang (tone 1). */
public final class Dang1 {
    private Dang1() {}

    /** 当 (dang1) — serve as. */
    public static final ChineseCharacterEntry 当_SERVE_AS = entry("当")
            .py(D, OPEN, Body.A, Tail.NG, T1).strokes(6).radical(42)
            .topBottom(zi("⺌"), zi("彐"))
            .phonoSemantic(zi("彐"), zi("⺌"));

    public static final List<ChineseCharacterEntry> ALL = List.of(当_SERVE_AS);
}
