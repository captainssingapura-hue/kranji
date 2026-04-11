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

/** Characters pronounced ao (tone 2). */
public final class Ao2 {
    private Ao2() {}

    /** 遨 (ao) — roam. SemiEnclosureBL: 辶 + 敖. Phono-semantic. */
    public static final ChineseCharacterEntry 遨_ROAM = entry("遨")
            .py(ZERO, OPEN, Body.A, Tail.VOWEL_U, T2).strokes(13).radical(162)
            .semiEnclosureBL(ZOU_ZHI_DI, zi("敖"))
            .phonoSemantic(ZOU_ZHI_DI, zi("敖"));

    public static final List<ChineseCharacterEntry> ALL = List.of(遨_ROAM);
}
