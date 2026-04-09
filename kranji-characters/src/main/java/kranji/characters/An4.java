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

/** Characters pronounced an (tone 4). */
public final class An4 {
    private An4() {}

    /** 按 (an4) — press; according. */
    public static final ChineseCharacterEntry 按_PRESS_ACCORDING = entry("按")
            .py(ZERO, OPEN, Body.A, Tail.N, T4).strokes(9).radical(64)
            .leftRight(TI_SHOU_PANG, zi("安"))
            .phonoSemantic(TI_SHOU_PANG, zi("安"));

    /** 暗 (an4) — dark; hidden. */
    public static final ChineseCharacterEntry 暗_DARK_HIDDEN = entry("暗")
            .py(ZERO, OPEN, Body.A, Tail.N, T4).strokes(13).radical(72)
            .leftRight(zi("日"), tb(zi("立"), zi("日")))
            .phonoSemantic(zi("日"), zi("音"));

    public static final List<ChineseCharacterEntry> ALL = List.of(按_PRESS_ACCORDING, 暗_DARK_HIDDEN);
}
