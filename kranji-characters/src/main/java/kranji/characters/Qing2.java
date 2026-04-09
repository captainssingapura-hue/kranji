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

/** Characters pronounced qing (tone 2). */
public final class Qing2 {
    private Qing2() {}

    /** 情 (qing) — emotion. LeftRight: 忄 + 青. Phono-semantic. */
    public static final ChineseCharacterEntry 情_EMOTION = entry("情")
            .py(Q, OPEN, Body.I, Tail.NG, T2).strokes(11).radical(61)
            .leftRight(SHU_XIN_PANG, zi("青"))
            .phonoSemantic(SHU_XIN_PANG, zi("青"));

    /** 晴 (qing) — sunny. LeftRight: 日 + 青. Phono-semantic. */
    public static final ChineseCharacterEntry 晴_SUNNY = entry("晴")
            .py(Q, OPEN, Body.I, Tail.NG, T2).strokes(12).radical(72)
            .leftRight(zi("日"), zi("青"))
            .phonoSemantic(zi("日"), zi("青"));

    public static final List<ChineseCharacterEntry> ALL = List.of(情_EMOTION, 晴_SUNNY);
}
