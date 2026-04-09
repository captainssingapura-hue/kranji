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

/** Characters pronounced zhi (tone 1). */
public final class Zhi1 {
    private Zhi1() {}

    /** 之 (zhi1) — of; go. */
    public static final ChineseCharacterEntry 之_OF_GO = entry("之")
            .py(ZH, OPEN, Body.NULL, Tail.NONE, T1).strokes(3).radical(4)
            .singular()
            .pictograph();

    /** 知 (zhi1) — know. */
    public static final ChineseCharacterEntry 知_KNOW = entry("知")
            .py(ZH, OPEN, Body.NULL, Tail.NONE, T1).strokes(8).radical(111)
            .leftRight(zi("矢"), zi("口"))
            .phonoSemantic(zi("口"), zi("矢"));

    /** 支 (zhi1) — support; branch. */
    public static final ChineseCharacterEntry 支_SUPPORT_BRANCH = entry("支")
            .py(ZH, OPEN, Body.NULL, Tail.NONE, T1).strokes(4).radical(65)
            .singular()
            .compoundIndicative("support; branch");

    /** 织 (zhi1) — weave; organize. */
    public static final ChineseCharacterEntry 织_WEAVE_ORGANIZE = entry("织")
            .py(ZH, OPEN, Body.NULL, Tail.NONE, T1).strokes(8).radical(120)
            .leftRight(JIAO_SI_PANG, zi("只"))
            .phonoSemantic(JIAO_SI_PANG, zi("只"));

    public static final List<ChineseCharacterEntry> ALL = List.of(之_OF_GO, 知_KNOW, 支_SUPPORT_BRANCH, 织_WEAVE_ORGANIZE);
}
