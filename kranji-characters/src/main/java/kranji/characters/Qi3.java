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

/** Characters pronounced qi (tone 3). */
public final class Qi3 {
    private Qi3() {}

    /** 起 (qi3) — rise; start. */
    public static final ChineseCharacterEntry 起_RISE_START = entry("起")
            .py(Q, OPEN, Body.I, Tail.NONE, T3).strokes(10).radical(156)
            .semiEnclosureBL(zi("走"), zi("己"))
            .phonoSemantic(zi("走"), zi("己"));

    /** 启 (qi3) — open; start. */
    public static final ChineseCharacterEntry 启_OPEN_START = entry("启")
            .py(Q, OPEN, Body.I, Tail.NONE, T3).strokes(7).radical(30)
            .topBottom(zi("户"), zi("口"))
            .compoundIndicative("open; start");

    public static final List<ChineseCharacterEntry> ALL = List.of(起_RISE_START, 启_OPEN_START);
}
