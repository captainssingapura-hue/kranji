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

/** Characters pronounced xiang (tone 4). */
public final class Xiang4 {
    private Xiang4() {}

    /** 向 (xiang4) — toward. */
    public static final ChineseCharacterEntry 向_TOWARD = entry("向")
            .py(X, I, Body.A, Tail.NG, T4).strokes(6).radical(30)
            .semiEnclosureUL(zi("丿"), zi("向"))
            .pictograph();

    /** 象 (xiang4) — elephant; image. */
    public static final ChineseCharacterEntry 象_ELEPHANT_IMAGE = entry("象")
            .py(X, I, Body.A, Tail.NG, T4).strokes(11).radical(152)
            .singular()
            .pictograph();

    /** 像 (xiang4) — resemble; image. */
    public static final ChineseCharacterEntry 像_RESEMBLE_IMAGE = entry("像")
            .py(X, I, Body.A, Tail.NG, T4).strokes(13).radical(9)
            .leftRight(DAN_REN_PANG, zi("象"))
            .phonoSemantic(DAN_REN_PANG, zi("象"));

    public static final List<ChineseCharacterEntry> ALL = List.of(向_TOWARD, 象_ELEPHANT_IMAGE, 像_RESEMBLE_IMAGE);
}
