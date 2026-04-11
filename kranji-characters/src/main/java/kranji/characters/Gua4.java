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

/** Characters pronounced gua (tone 4). */
public final class Gua4 {
    private Gua4() {}

    /** 挂 (gua4) — hang; call. */
    public static final ChineseCharacterEntry 挂_HANG_CALL = entry("挂")
            .py(G, U, Body.A, Tail.NONE, T4).strokes(9).radical(64)
            .leftRight(TI_SHOU_PANG, zi("卦"))
            .phonoSemantic(TI_SHOU_PANG, zi("卦"));

    public static final List<ChineseCharacterEntry> ALL = List.of(挂_HANG_CALL);
}
