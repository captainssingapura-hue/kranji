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

/** Characters pronounced ke (tone 3). */
public final class Ke3 {
    private Ke3() {}

    /** 渴 (ke3) — thirsty. */
    public static final ChineseCharacterEntry 渴_THIRSTY = entry("渴")
            .py(K, OPEN, Body.E, Tail.NONE, T3).strokes(12).radical(85)
            .leftRight(SAN_DIAN_SHUI, zi("曷"))
            .phonoSemantic(SAN_DIAN_SHUI, zi("曷"));

    public static final List<ChineseCharacterEntry> ALL = List.of(渴_THIRSTY);
}
