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

/** Characters pronounced yuan (tone 4). */
public final class Yuan4 {
    private Yuan4() {}

    /** 院 (yuan4) — courtyard. */
    public static final ChineseCharacterEntry 院_COURTYARD = entry("院")
            .py(ZERO, V, Body.A, Tail.N, T4).strokes(9).radical(170)
            .leftRight(zi("阝"), zi("完"))
            .phonoSemantic(zi("阝"), zi("完"));

    /** 愿 (yuan4) — willing; wish. */
    public static final ChineseCharacterEntry 愿_WILLING_WISH = entry("愿")
            .py(ZERO, V, Body.A, Tail.N, T4).strokes(14).radical(61)
            .topBottom(zi("原"), zi("心"))
            .phonoSemantic(zi("心"), zi("原"));

    public static final List<ChineseCharacterEntry> ALL = List.of(院_COURTYARD, 愿_WILLING_WISH);
}
