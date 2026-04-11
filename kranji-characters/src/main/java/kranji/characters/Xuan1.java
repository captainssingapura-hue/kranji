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

/** Characters pronounced xuan (tone 1). */
public final class Xuan1 {
    private Xuan1() {}

    /** 宣 (xuan1) — declare; spread. */
    public static final ChineseCharacterEntry 宣_DECLARE_SPREAD = entry("宣")
            .py(X, V, Body.A, Tail.N, T1).strokes(9).radical(40)
            .topBottom(BAO_GAI_TOU, zi("亘"))
            .phonoSemantic(BAO_GAI_TOU, zi("亘"));

    public static final List<ChineseCharacterEntry> ALL = List.of(宣_DECLARE_SPREAD);
}
