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

/** Characters pronounced yu (tone 3). */
public final class Yu3 {
    private Yu3() {}

    /** 与 (yu3) — and; give. */
    public static final ChineseCharacterEntry 与_AND_GIVE = entry("与")
            .py(ZERO, V, Body.V, Tail.NONE, T3).strokes(3).radical(1)
            .singular()
            .pictograph();

    /** 雨 (yu3) — rain. */
    public static final ChineseCharacterEntry 雨_RAIN = entry("雨")
            .py(ZERO, V, Body.V, Tail.NONE, T3).strokes(8).radical(173)
            .singular()
            .pictograph();

    /** 予 (yu3) — give; grant. */
    public static final ChineseCharacterEntry 予_GIVE_GRANT = entry("予")
            .py(ZERO, V, Body.V, Tail.NONE, T3).strokes(4).radical(6)
            .singular()
            .pictograph();

    /** 宇 (yu3) — universe; eave. */
    public static final ChineseCharacterEntry 宇_UNIVERSE_EAVE = entry("宇")
            .py(ZERO, V, Body.V, Tail.NONE, T3).strokes(6).radical(40)
            .topBottom(BAO_GAI_TOU, zi("于"))
            .phonoSemantic(BAO_GAI_TOU, zi("于"));

    public static final List<ChineseCharacterEntry> ALL = List.of(与_AND_GIVE, 雨_RAIN, 予_GIVE_GRANT, 宇_UNIVERSE_EAVE);
}
