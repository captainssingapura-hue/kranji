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

/** Characters pronounced tong (tone 1). */
public final class Tong1 {
    private Tong1() {}

    /** 通 (tong1) — through; pass. */
    public static final ChineseCharacterEntry 通_THROUGH_PASS = entry("通")
            .py(T, OPEN, Body.O, Tail.NG, T1).strokes(10).radical(162)
            .semiEnclosureBL(ZOU_ZHI_DI, zi("甬"))
            .phonoSemantic(ZOU_ZHI_DI, zi("甬"));

    public static final List<ChineseCharacterEntry> ALL = List.of(通_THROUGH_PASS);
}
