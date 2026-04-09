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

/** Characters pronounced xie (tone 4). */
public final class Xie4 {
    private Xie4() {}

    /** 谢 (xie4) — thank. */
    public static final ChineseCharacterEntry 谢_THANK = entry("谢")
            .py(X, I, Body.E_CARON, Tail.NONE, T4).strokes(12).radical(149)
            .leftMiddleRight(YAN_ZI_PANG, zi("身"), zi("寸"))
            .phonoSemantic(YAN_ZI_PANG, zi("射"));

    public static final List<ChineseCharacterEntry> ALL = List.of(谢_THANK);
}
