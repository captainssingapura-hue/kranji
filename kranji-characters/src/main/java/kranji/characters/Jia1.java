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

/** Characters pronounced jia (tone 1). */
public final class Jia1 {
    private Jia1() {}

    /** 家 (jia1) — home; family. */
    public static final ChineseCharacterEntry 家_HOME_FAMILY = entry("家")
            .py(J, I, Body.A, Tail.NONE, T1).strokes(10).radical(40)
            .topBottom(BAO_GAI_TOU, zi("豕"))
            .compoundIndicative("home; family");

    /** 加 (jia1) — add; plus. */
    public static final ChineseCharacterEntry 加_ADD_PLUS = entry("加")
            .py(J, I, Body.A, Tail.NONE, T1).strokes(5).radical(19)
            .leftRight(zi("力"), zi("口"))
            .phonoSemantic(zi("口"), zi("力"));

    public static final List<ChineseCharacterEntry> ALL = List.of(家_HOME_FAMILY, 加_ADD_PLUS);
}
