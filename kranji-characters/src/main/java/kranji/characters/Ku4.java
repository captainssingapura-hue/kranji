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

/** Characters pronounced ku (tone 4). */
public final class Ku4 {
    private Ku4() {}

    /** 裤 (ku4) — pants. */
    public static final ChineseCharacterEntry 裤_PANTS = entry("裤")
            .py(K, U, Body.U, Tail.NONE, T4).strokes(12).radical(145)
            .leftRight(YI_ZI_PANG, zi("库"))
            .phonoSemantic(YI_ZI_PANG, zi("库"));

    public static final List<ChineseCharacterEntry> ALL = List.of(裤_PANTS);
}
