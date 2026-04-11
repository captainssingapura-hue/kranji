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

/** Characters pronounced zi (tone 4). */
public final class Zi4 {
    private Zi4() {}

    /** 字 (zi) — character. TopBottom: 宀 + 子. Phono-semantic. */
    public static final ChineseCharacterEntry 字_CHARACTER = entry("字")
            .py(Z, OPEN, Body.NULL, Tail.NONE, T4).strokes(6).radical(39)
            .topBottom(BAO_GAI_TOU, zi("子"))
            .phonoSemantic(BAO_GAI_TOU, zi("子"));

    public static final List<ChineseCharacterEntry> ALL = List.of(字_CHARACTER);
}
