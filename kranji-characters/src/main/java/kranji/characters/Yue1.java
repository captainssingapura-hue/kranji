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

/** Characters pronounced yue (tone 1). */
public final class Yue1 {
    private Yue1() {}

    /** 约 (yue1) — about; promise. */
    public static final ChineseCharacterEntry 约_ABOUT_PROMISE = entry("约")
            .py(ZERO, V, Body.E_CARON, Tail.NONE, T1).strokes(6).radical(120)
            .leftRight(JIAO_SI_PANG, zi("勺"))
            .phonoSemantic(JIAO_SI_PANG, zi("勺"));

    public static final List<ChineseCharacterEntry> ALL = List.of(约_ABOUT_PROMISE);
}
