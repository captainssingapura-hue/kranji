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

/** Characters pronounced reng (tone 1). */
public final class Reng1 {
    private Reng1() {}

    /** 扔 (reng1) — throw. */
    public static final ChineseCharacterEntry 扔_THROW = entry("扔")
            .py(R, OPEN, Body.E, Tail.NG, T1).strokes(5).radical(64)
            .leftRight(TI_SHOU_PANG, zi("乃"))
            .phonoSemantic(TI_SHOU_PANG, zi("乃"));

    public static final List<ChineseCharacterEntry> ALL = List.of(扔_THROW);
}
