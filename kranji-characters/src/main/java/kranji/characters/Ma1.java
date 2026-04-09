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

/** Characters pronounced ma (tone 1). */
public final class Ma1 {
    private Ma1() {}

    /** 妈 (ma1) — mother; mom. */
    public static final ChineseCharacterEntry 妈_MOTHER_MOM = entry("妈")
            .py(M, OPEN, Body.A, Tail.NONE, T1).strokes(6).radical(38)
            .leftRight(zi("女"), zi("马"))
            .phonoSemantic(zi("女"), zi("马"));

    public static final List<ChineseCharacterEntry> ALL = List.of(妈_MOTHER_MOM);
}
