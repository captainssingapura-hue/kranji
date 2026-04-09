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

/** Characters pronounced hua (tone 4). */
public final class Hua4 {
    private Hua4() {}

    /** 化 (hua4) — change; -ize. */
    public static final ChineseCharacterEntry 化_CHANGE_IZE = entry("化")
            .py(H, U, Body.A, Tail.NONE, T4).strokes(4).radical(21)
            .leftRight(DAN_REN_PANG, zi("匕"))
            .compoundIndicative("change; -ize");

    /** 话 (hua4) — speech; word. */
    public static final ChineseCharacterEntry 话_SPEECH_WORD = entry("话")
            .py(H, U, Body.A, Tail.NONE, T4).strokes(8).radical(149)
            .leftRight(YAN_ZI_PANG, zi("舌"))
            .phonoSemantic(YAN_ZI_PANG, zi("舌"));

    /** 画 (hua4) — draw; painting. */
    public static final ChineseCharacterEntry 画_DRAW_PAINTING = entry("画")
            .py(H, U, Body.A, Tail.NONE, T4).strokes(8).radical(102)
            .singular()
            .compoundIndicative("draw; painting");

    public static final List<ChineseCharacterEntry> ALL = List.of(化_CHANGE_IZE, 话_SPEECH_WORD, 画_DRAW_PAINTING);
}
