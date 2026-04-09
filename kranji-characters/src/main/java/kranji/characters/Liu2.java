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

/** Characters pronounced liu (tone 2). */
public final class Liu2 {
    private Liu2() {}

    /** 留 (liu2) — stay; keep. */
    public static final ChineseCharacterEntry 留_STAY_KEEP = entry("留")
            .py(L, I, Body.O, Tail.VOWEL_U, T2).strokes(10).radical(102)
            .topBottom(zi("卯"), zi("田"))
            .phonoSemantic(zi("田"), zi("卯"));

    /** 刘 (liu2) — surname Liu. */
    public static final ChineseCharacterEntry 刘_SURNAME_LIU = entry("刘")
            .py(L, I, Body.O, Tail.VOWEL_U, T2).strokes(6).radical(18)
            .leftRight(zi("文"), LI_DAO_PANG)
            .phonoSemantic(LI_DAO_PANG, zi("文"));

    public static final List<ChineseCharacterEntry> ALL = List.of(留_STAY_KEEP, 刘_SURNAME_LIU);
}
